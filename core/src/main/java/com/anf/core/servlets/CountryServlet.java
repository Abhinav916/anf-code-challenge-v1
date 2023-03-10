package com.anf.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.anf.core.config.ANFConfig;
import com.day.cq.dam.api.Asset;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Component(service = {Servlet.class}, property = {"process.label=Country API",
        Constants.SERVICE_DESCRIPTION + "= This servlet sets countries data in datasource"})
@SlingServletPaths(value = "/bin/country/options")
@Designate(ocd = ANFConfig.class)
public class CountryServlet extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(CountryServlet.class);

    private ANFConfig anfConfig;

    @Activate
    @Modified
    public void activate(ANFConfig config) {
        this.anfConfig = config;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            Resource jsonResource = resourceResolver.getResource(anfConfig.countryJsonPath());
            assert jsonResource != null;

            Asset jsonAsset = jsonResource.adaptTo(Asset.class);
            assert jsonAsset != null;
            InputStream assetStream = jsonAsset.getOriginal().getStream();

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            assert assetStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetStream, StandardCharsets.UTF_8));

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            setDataSource(request, stringBuilder.toString());

        } catch (JSONException | IOException e) {
            LOG.error("Could not extract json data={}", e.getMessage());
        }
    }

    private void setDataSource(SlingHttpServletRequest request, String jsonString) throws JSONException {
        List<Resource> resources = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        Iterator<String> jsonKeys = jsonObject.keys();
        ValueMap valueMap;
        while (jsonKeys.hasNext()) {
            String jsonKey = jsonKeys.next();

            valueMap = new ValueMapDecorator(new HashMap<>());
            valueMap.put("text", jsonKey);
            valueMap.put("value", jsonObject.getString(jsonKey));
            resources.add(new ValueMapResource(request.getResourceResolver(), new ResourceMetadata(), "nt:unstructured", valueMap));
        }

        //Create DataSource and set as request attribute
        //so that it can be accessible through model class-
        //com.adobe.cq.wcm.core.components.internal.models.v1.form.OptionsImpls

        DataSource dataSource = new SimpleDataSource(resources.iterator());
        request.setAttribute(DataSource.class.getName(), dataSource);
    }
    /* End Code*/
}
