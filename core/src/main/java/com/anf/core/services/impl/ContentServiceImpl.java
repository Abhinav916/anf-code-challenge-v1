package com.anf.core.services.impl;

import com.anf.core.component.CommonUtils;
import com.anf.core.config.ANFConfig;
import com.anf.core.services.ContentService;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Component(immediate = true, service = ContentService.class)
@Designate(ocd = ANFConfig.class)
public class ContentServiceImpl implements ContentService {

   @Reference
    private CommonUtils commonUtils;

    private ANFConfig anfConfig;

    @Activate
    @Modified
    public void activate(ANFConfig config) {
        this.anfConfig = config;
    }

    @Override
    public void commitUserDetails(SlingHttpServletRequest req) throws PersistenceException, LoginException {
        // Add your logic. Modify method signature as per need.
        ResourceResolver resourceResolver = commonUtils.getResourceResolver();
        //create path /anf-code-challenge if not present in /var
        Resource dataStorePathResource = ResourceUtil.getOrCreateResource(resourceResolver, anfConfig.dataStorePath(),JcrConstants.NT_UNSTRUCTURED, null, true);
		if (Objects.nonNull(dataStorePathResource)) {
            Map<String, Object> userDataMap = new HashMap<>();
    		userDataMap.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);

            userDataMap.put("firstName", StringUtils.defaultIfBlank(req.getParameter("firstName"), ""));
            userDataMap.put("lastName", StringUtils.defaultIfBlank(req.getParameter("lastName"), ""));

            userDataMap.put("age", StringUtils.defaultIfBlank(req.getParameter("age"), ""));

            userDataMap.put("country", StringUtils.defaultIfBlank(req.getParameter("country"), ""));

            resourceResolver.create(dataStorePathResource, String.valueOf(UUID.randomUUID()), userDataMap);
            resourceResolver.commit();
		}
    }

    @Override
    public boolean validateAge(SlingHttpServletRequest req){
        Boolean isValidAge = Boolean.FALSE;
        String ageParam = req.getParameter("age");
        if(StringUtils.isNotBlank(ageParam) && StringUtils.isNumeric(ageParam)){
            long currentAge = Long.parseLong(ageParam);
            Resource ageResource = req.getResourceResolver().getResource(anfConfig.ageLimitPath());
            if(Objects.nonNull(ageResource)){
                ValueMap valueMap = ageResource.adaptTo(ValueMap.class);
                if(Objects.nonNull(valueMap)){
                    int minAge = Integer.parseInt(Objects.requireNonNull(valueMap.get("minAge", String.class)));
                    int maxAge = Integer.parseInt(Objects.requireNonNull(valueMap.get("maxAge", String.class)));
                    if(currentAge >= minAge && currentAge <= maxAge) {
                        isValidAge = Boolean.TRUE;
                    }
                }
            }
        }
        return isValidAge;
    }

    /* End Code*/
}
