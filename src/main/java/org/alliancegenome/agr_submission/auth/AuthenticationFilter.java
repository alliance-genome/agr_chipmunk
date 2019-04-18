package org.alliancegenome.agr_submission.auth;

import java.io.IOException;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Inject
	@AuthenticatedUser
	Event<String> userAuthenticatedEvent;
	
	private static final String REALM = "AGR";
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.info("AuthenticationFilter: filter: " + requestContext);
		// Get the Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		log.info("AuthenticationFilter: filter: " + authorizationHeader);
		
		// Validate the Authorization header
		if (!isTokenBasedAuthentication(authorizationHeader)) {
			abortWithUnauthorized(requestContext);
			return;
		}

		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

		try {
			validateToken(token);

		} catch (Exception e) {
			abortWithUnauthorized(requestContext);
		}
	}

	private boolean isTokenBasedAuthentication(String authorizationHeader) {
		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Bearer" plus a whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {

		// Abort the filter chain with a 401 status code response
		// The WWW-Authenticate header is sent along with the response
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build());
	}

	private void validateToken(String token) throws Exception {
		// Check if the token was issued by the server and if it's not expired
		// Throw an Exception if the token is invalid
		log.info("Validating Token: " + token);
		// Do a lookup of the user and fire the event
		//userAuthenticatedEvent.fire(username);
	}
}
