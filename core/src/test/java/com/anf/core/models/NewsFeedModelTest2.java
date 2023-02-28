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
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.servlethelpers.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Simple JUnit test verifying the NewsFeedModelTest
 */

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 *
 *  * Test case by importing content through JSON
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class NewsFeedModelTest2 {

    private NewsFeedModel newsFeedModel;

    private Page page;
    private Resource resource;

    @BeforeEach
    public void setup(AemContext context) throws Exception {

        context.addModelsForClasses(NewsFeedModel.class);
        
        context.load().json("/com.anf.core.models/NewsFeedModelTest.json", "/var/commerce/products/anf-code-challenge/newsData");
        
        MockSlingHttpServletRequest request = context.request();
        request.setResource(context.resourceResolver().getResource("/var/commerce/products/anf-code-challenge/newsData"));        

        // create sling model
        newsFeedModel = resource.adaptTo(NewsFeedModel.class);
    }

    @Test
    void testGetNewsFeedList() throws Exception {
        // some very basic junit tests
        List<NewsFeed> newsFeedList = newsFeedModel.getNewsFeeds();
        assertNotNull(newsFeedList);
    }
    /* End Code*/
}
