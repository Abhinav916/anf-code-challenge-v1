package com.anf.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;

import javax.jcr.RepositoryException;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
public interface ContentService {
	void commitUserDetails(SlingHttpServletRequest req) throws PersistenceException, LoginException;
	boolean validateAge(SlingHttpServletRequest req);

	/* End Code*/
}
