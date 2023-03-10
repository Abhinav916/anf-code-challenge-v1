package com.anf.core.servlets;

import com.anf.core.component.CommonUtils;
import com.anf.core.config.ANFConfig;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
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

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Component(service = {Servlet.class})
@SlingServletResourceTypes(resourceTypes = "anf-code-challenge/components/page",
        methods = HttpConstants.METHOD_GET, selectors = "queryBuilder", extensions = "txt")
@ServiceDescription("Retrieve First 10 pages with Query Builder API")
@Designate(ocd = ANFConfig.class)
public class PageListByQueryBuilderAPI extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(PageListByQueryBuilderAPI.class.getName());

    @Reference
    CommonUtils commonUtils;

    @Reference
    QueryBuilder queryBuilder;

    ANFConfig anfConfig;

    @Activate
    @Modified
    public void activate(ANFConfig config) {
        this.anfConfig = config;
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) {
        try (ResourceResolver resourceResolver = req.getResourceResolver()) {

            final Session session = resourceResolver.adaptTo(Session.class);

            //Creating Predicate KEY_VALUE pair with HashMap
            HashMap<String, String> predicateMap = new HashMap<>();
            predicateMap.put("path", anfConfig.pageContentPath());
            predicateMap.put("type", "cq:Page");
            predicateMap.put("property", "jcr:content/anfCodeChallenge");
            predicateMap.put("property.operation", "exists");
            predicateMap.put("orderby", "@jcr:created");
            predicateMap.put("p.limit", "10");

            //Creating Query using through predicate key_value
            Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);
            SearchResult result = query.getResult();

            //store all the page paths in LinkedList
            List<String> pageList = new LinkedList<>();
            for (Hit hit : result.getHits()) {
                pageList.add(hit.getResource().getPath());
            }

            resp.setContentType("text/plain");
            resp.getWriter().write("Retrieved First 10 Pages Using Query Builder API" + pageList);

        } catch (RepositoryException | IOException e) {
            LOG.error("Error in Query Builder API = {}", e.getMessage());
        }
    }
    /* End Code*/
}
