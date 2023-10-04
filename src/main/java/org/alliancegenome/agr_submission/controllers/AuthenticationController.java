package org.alliancegenome.agr_submission.controllers;

import org.alliancegenome.agr_submission.auth.AuthToken;
import org.alliancegenome.agr_submission.auth.Credentials;
import org.alliancegenome.agr_submission.dao.UserDAO;
import org.alliancegenome.agr_submission.interfaces.server.AuthenticationControllerInterface;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class AuthenticationController implements AuthenticationControllerInterface {

	@Inject UserDAO userDAO;
	
	public Response authenticateUser(Credentials creds) {
		try {
			authenticate(creds.getUsername(), creds.getPassword());
			AuthToken token = issueToken(creds.getUsername());
			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}	   
	}

	private void authenticate(String username, String password) throws Exception {
		// Authenticate against a database, LDAP, file or whatever
		// Throw an Exception if the credentials are invalid
		// Look userDAO
	}

	private AuthToken issueToken(String username) {
		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token
		AuthToken token = new AuthToken();
		token.setApiKey("Bearer This is a test");
		return token;
	}
}
