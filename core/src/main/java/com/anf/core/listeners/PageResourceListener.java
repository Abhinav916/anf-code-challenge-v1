package com.anf.core.listeners;

import com.anf.core.component.CommonUtils;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Component(service = ResourceChangeListener.class, property = {
        ResourceChangeListener.PATHS + "=" + "/content/anf-code-challenge/us/en",
        ResourceChangeListener.CHANGES + "=" + "ADDED"
})
public class PageResourceListener implements ResourceChangeListener {

    private static final Logger LOG = LoggerFactory.getLogger(PageResourceListener.class);

    @Reference
    CommonUtils commonUtils;

    @Override
    public void onChange(List<ResourceChange> arg0) {
        arg0.forEach(change -> {
            String paegResourcePath = change.getPath();
            //Add the property to jcr:content
            if (paegResourcePath.endsWith(JcrConstants.JCR_CONTENT)) {
                try (ResourceResolver resourceResolver = commonUtils.getResourceResolver()) {
                    Resource resource = Objects.requireNonNull(resourceResolver.getResource(paegResourcePath));
                    //Put the ModifiableValueMap to update the page created properties
                    ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
                    assert modifiableValueMap != null;
                    //Add the pageCreated Properties
                    modifiableValueMap.put("pageCreated", true);
                    //Committing the resource resolver
                    resource.getResourceResolver().commit();
                } catch (PersistenceException | org.apache.sling.api.resource.LoginException e) {
                    LOG.error("An Error Occurred in Resource Change Listener while creating Page : {}", e.getMessage());
                }
            }
        });
    }
    /* End Code*/
}
