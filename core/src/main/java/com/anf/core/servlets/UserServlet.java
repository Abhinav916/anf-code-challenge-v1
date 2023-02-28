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
package com.anf.core.servlets;

import com.anf.core.services.ContentService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Component(service = { Servlet.class })
@SlingServletPaths(
        value = "/bin/saveUserDetails"
)
public class UserServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class.getName());

    @Reference
    private ContentService contentService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        // Make use of ContentService to write the business logic

        //call the doPost
        doPost(req, resp);
    }

    /**
     * this method can be used when making post call for data submission
     */
    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        boolean isValidAge = contentService.validateAge(req);
        try{
            if(isValidAge){
                //write data to repo once age is valid
                contentService.commitUserDetails(req);
                resp.setStatus(HttpStatus.SC_OK);
            }
        } catch (Exception e){
            isValidAge = false;
            resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            LOG.error("Error saving user details", e);
        }
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().println(isValidAge);
    }

    /* End Code*/
}
