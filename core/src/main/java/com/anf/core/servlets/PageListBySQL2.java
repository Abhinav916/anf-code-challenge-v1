package com.anf.core.servlets;

import com.anf.core.component.CommonUtils;
import com.anf.core.config.ANFConfig;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Component(service = {Servlet.class})
@SlingServletResourceTypes(resourceTypes = "anf-code-challenge/components/page",
        methods = HttpConstants.METHOD_GET, selectors = "sql2", extensions = "txt")
@ServiceDescription("Retrieve First 10 pages with JCR SQL2 Query")
@Designate(ocd= ANFConfig.class)
public class PageListBySQL2 extends SlingSafeMethodsServlet{

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(PageListBySQL2.class.getName());

    @Reference
    CommonUtils commonUtils;

    ANFConfig anfConfig;

    @Activate
    @Modified
    public void activate(ANFConfig config){
        this.anfConfig = config;
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) {
        try (ResourceResolver resourceResolver = req.getResourceResolver()) {

            String queryString = "SELECT page.* FROM [cq:Page] AS page INNER JOIN [cq:PageContent] AS jcrcontent ON ISCHILDNODE(jcrcontent, page) WHERE ISDESCENDANTNODE(page, \"" + anfConfig.pageContentPath() + "\") AND jcrcontent.[anfCodeChallenge] IS NOT NULL ORDER BY jcrcontent.[jcr:created] ASC";
            final Session session = resourceResolver.adaptTo(Session.class);

            QueryManager queryManager = session.getWorkspace().getQueryManager();

            Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
            query.setLimit(10);
            
            final QueryResult queryResult = query.execute();
            NodeIterator pages = queryResult.getNodes();
            List<String> pageList = new LinkedList<>();

            //store all the page paths in LinkedList
            while (pages.hasNext()) {
                pageList.add(pages.nextNode().getPath());
            }

            resp.setContentType("text/plain");
            resp.getWriter().write("Retrieved First 10 Pages Using JCR-SQL2" + pageList);

        } catch (RepositoryException | IOException e) {
            LOG.error("Error in JCR-SQL2 = {}", e.getMessage());
        }
    }
    /* End Code*/
}
