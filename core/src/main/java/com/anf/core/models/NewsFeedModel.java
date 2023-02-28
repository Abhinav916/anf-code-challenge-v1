package com.anf.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.anf.core.bean.NewsFeed;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class NewsFeedModel {
    private static final Logger LOG = LoggerFactory.getLogger(NewsFeedModel.class.getName());

    private static final String NEWS_FEED_DATA_PATH = "/var/commerce/products/anf-code-challenge/newsData";

    private List<NewsFeed> newsFeeds;

    @SlingObject
    private ResourceResolver resourceResolver;

    @PostConstruct
    protected void init() {
        newsFeeds = new ArrayList<>();

        Resource newResource = resourceResolver.getResource(NEWS_FEED_DATA_PATH);
        //Getting news feed resource
        if (Objects.nonNull(newResource)) {
            LOG.info("News Feed Data Found");
            Iterator<Resource> resource = newResource.listChildren();
            while (resource.hasNext()) {
                //Getting ValueMap
                ValueMap valueMap = resource.next().getValueMap();
                NewsFeed newsFeed = new NewsFeed();
                newsFeed.setTitle(valueMap.get("title", String.class));
                newsFeed.setAuthor(valueMap.get("author", String.class));
                newsFeed.setDescription(valueMap.get("description", String.class));
                newsFeed.setUrl(valueMap.get("url", String.class));
                newsFeed.setUrlImage(valueMap.get("urlImage", String.class));
                //store all the value in list
                newsFeeds.add(newsFeed);
            }
        }
    }

    public List<NewsFeed> getNewsFeeds() {
        return newsFeeds;
    }

    /* End Code*/

}
