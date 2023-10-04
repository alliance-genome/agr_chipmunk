package org.alliancegenome.agr_submission.auth;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;

@RequestScoped
public class AuthenticatedUserProducer {

	@Produces
	@RequestScoped
	@AuthenticatedUser
	AuthedUser authenticatedUser = new AuthedUser();

	public void handleAuthenticationEvent(@Observes @AuthenticatedUser AuthedUser user) {
		this.authenticatedUser = user;
	}
}
