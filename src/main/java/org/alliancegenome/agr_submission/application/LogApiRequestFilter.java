package org.alliancegenome.agr_submission.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.alliancegenome.agr_submission.auth.AuthedUser;
import org.alliancegenome.agr_submission.auth.AuthenticatedUser;
import org.alliancegenome.agr_submission.entities.log.LogApiDTO;

@Provider
public class LogApiRequestFilter implements ContainerRequestFilter {

	@Context
	private HttpServletRequest servletRequest;
	
	@Inject @Any Event<LogApiDTO> apiEvent;

	@Inject
	@AuthenticatedUser
	AuthedUser authenticatedUser;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		LogApiDTO info = new  LogApiDTO();

		if(authenticatedUser.getUser() != null) {
			info.setUser(authenticatedUser.getUser());
		}
		info.setRequestMethod(requestContext.getMethod());
		
		HashMap<String, List<String>> headerMap = new HashMap<String, List<String>>(requestContext.getHeaders());
		headerMap.remove("Authorization");
		
		info.setHeadersString(headerMap.toString());
		info.setAddress(servletRequest.getRemoteAddr());
		List<String> userAgent = requestContext.getHeaders().get("User-Agent"); 
		if(userAgent != null) {
			info.setUserAgent(userAgent);
		}
		//info.setPath(requestContext.getUriInfo().getPath());
		//info.setAbsolutePath(requestContext.getUriInfo().getAbsolutePath().toString());
		
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		for(String key: requestContext.getUriInfo().getQueryParameters().keySet()) {
			map.put(key, requestContext.getUriInfo().getQueryParameters().get(key));
		}
		info.setQueryParameters(map);
		
		map = new HashMap<String, List<String>>();
		for(String key: requestContext.getUriInfo().getPathParameters().keySet()) {
			map.put(key, requestContext.getUriInfo().getPathParameters().get(key));
		}
		info.setPathParameters(map);

		info.setRequestUri(requestContext.getUriInfo().getRequestUri().toString());
		//info.setBaseUri(requestContext.getUriInfo().getBaseUri().toString());

		apiEvent.fire(info);

	}

}
