package org.alliancegenome.agr_submission.auth;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

import org.alliancegenome.agr_submission.entities.User;

@RequestScoped
public class AuthenticatedUserProducer {

	@Produces
	@RequestScoped
	@AuthenticatedUser
	private User authenticatedUser;

	public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
		this.authenticatedUser = findUser(username);
	}

	private User findUser(String username) {
		// Hit the the database or a service to find a user by its username and return it
		// Return the User instance
		return new User();
	}
}
