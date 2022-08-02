package org.alliancegenome.agr_submission.auth;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

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
