package org.alliancegenome.agr_submission.interfaces.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.alliancegenome.agr_submission.auth.Credentials;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication Endpoints")
public interface AuthenticationControllerInterface {

	@POST
	public Response authenticateUser(Credentials creds);
}
