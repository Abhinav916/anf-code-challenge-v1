/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.anf.core.models;

import com.anf.core.bean.NewsFeed;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.servlethelpers.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simple JUnit test verifying the NewsFeedModelTest
 */

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class NewsFeedModelTest {

    private static String AUTHOR = "Caroline Fox";
    private static String TITLE = "UFC 273: Five things we learned as Alexander Volkanovski dominates 'Korean Zombie";
    private static String CONTENT = "Volkanovski earned a performance-of-the-night bonus for his win over Chan Sung Jung Alexander Volkanovski cemented his status as the world's best featherweight with a flawless victory over the Kore…";
    private static String DESCRIPTION = "BBC Sport looks at the big talking points from UFC 273 as Alexander Volkanovski beats the 'Korean Zombie' Chan Sung Jung to retain his featherweight title.";
    private static String URL = "https://www.bbc.co.uk/sport/mixed-martial-arts/61057214";
    private static String URL_IMAGE = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/B536/production/_124109364_gettyimages-1390585815.jpg";

    private NewsFeedModel newsFeedModel;

    private Page page;
    private Resource resource;

    @BeforeEach
    public void setup(AemContext context) throws Exception {

        context.addModelsForClasses(NewsFeedModel.class);
        ResourceUtil.getOrCreateResource(
                context.resourceResolver(),
                "/var/commerce/products/anf-code-challenge/newsData/news_0",
                Map.of(
                        "jcr:primaryType", JcrConstants.NT_UNSTRUCTURED,
                        "author", AUTHOR,
                        "content", CONTENT,
                        "description", DESCRIPTION,
                        "title", TITLE,
                        "url", URL,
                        "urlImage", URL_IMAGE
                ),
                JcrConstants.NT_UNSTRUCTURED,
                true);
        
        MockSlingHttpServletRequest request = context.request();
        request.setResource(context.resourceResolver().getResource("/var/commerce/products/anf-code-challenge/newsData"));        

        // create sling model
        newsFeedModel = request.adaptTo(NewsFeedModel.class);
    }

    @Test
    void testGetNewsFeedList() throws Exception {
        // some very basic junit tests
       List<NewsFeed> newsFeedList = newsFeedModel.getNewsFeeds();
       assertEquals(1, newsFeedList.size());
    }

    @Test
    void testGetAuthor() throws Exception {
        List<NewsFeed> newsFeedList = newsFeedModel.getNewsFeeds();
        NewsFeed feed = newsFeedList.get(0);

        assertEquals(AUTHOR, feed.getAuthor());
    }

    @Test
    void testGetTitle() throws Exception {
        List<NewsFeed> newsFeedList = newsFeedModel.getNewsFeeds();
        NewsFeed feed = newsFeedList.get(0);

        assertEquals(TITLE, feed.getTitle());
    }

    @Test
    void testGetDescription() throws Exception {
        List<NewsFeed> newsFeedList = newsFeedModel.getNewsFeeds();
        NewsFeed feed = newsFeedList.get(0);

        assertEquals(DESCRIPTION, feed.getDescription());
    }

    @Test
    void testGetUrl() throws Exception {
        List<NewsFeed> newsFeedList = newsFeedModel.getNewsFeeds();
        NewsFeed feed = newsFeedList.get(0);

        assertEquals(URL, feed.getUrl());
    }

    @Test
    void testGetUrlImage() throws Exception {
        List<NewsFeed> newsFeedList = newsFeedModel.getNewsFeeds();
        NewsFeed feed = newsFeedList.get(0);

        assertEquals(URL_IMAGE, feed.getUrlImage());
    }
    /* End Code*/
}
