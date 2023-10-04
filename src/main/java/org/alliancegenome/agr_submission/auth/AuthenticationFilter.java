package org.alliancegenome.agr_submission.auth;

import java.io.IOException;

import org.alliancegenome.agr_submission.dao.UserDAO;
import org.alliancegenome.agr_submission.entities.LoggedInUser;
import org.alliancegenome.agr_submission.util.AESUtil;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import io.quarkus.logging.Log;
import jakarta.annotation.Priority;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	@Inject
	@AuthenticatedUser
	Event<AuthedUser> userAuthenticatedEvent;
	
	@Inject UserDAO userDAO;
	
	private static final String REALM = "AGR";
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Log.info("AuthenticationFilter: filter: " + requestContext);
		// Get the Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		Log.debug("Authorization Header: " + authorizationHeader);

		if (!isTokenBasedAuthentication(authorizationHeader)) {
			abortWithUnauthorized(requestContext);
			return;
		}

		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

		try {
			validateToken(token);
		} catch (Exception e) {
			e.printStackTrace();
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
		//log.debug("API Access Token: " + ConfigHelper.getApiAccessToken());
		//log.debug("Validating Token: " + token);
		
		LoggedInUser user = userDAO.findUserByApiKey(token);
		AuthedUser authedUser = new AuthedUser();
		authedUser.setUser(user);
		userAuthenticatedEvent.fire(authedUser);
		
		if(user == null) {
			Log.warn("Authentication Unsuccessful: " + token);
			throw new Exception("Authentication Unsuccessful: " + token);
		}
		Config config = ConfigProvider.getConfig();
		String encryptionPasswordKey = config.getValue("encryption.passwordkey", String.class);
		Log.info("Authentication Successful for: " + AESUtil.decrypt(token, encryptionPasswordKey));
		
	}
}