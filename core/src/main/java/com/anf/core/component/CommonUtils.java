package com.anf.core.component;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@Component(service = CommonUtils.class, immediate = true)
public class CommonUtils {
    public static final String ANF_ADMIN_SUB_SERVICE = "anf-admin-service";
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    public ResourceResolver getResourceResolver() throws LoginException {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, ANF_ADMIN_SUB_SERVICE);
        return resourceResolverFactory.getServiceResourceResolver(param);
    }

    /* End Code*/
}
