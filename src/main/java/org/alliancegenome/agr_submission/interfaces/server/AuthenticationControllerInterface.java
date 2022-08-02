package org.alliancegenome.agr_submission.interfaces.server;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

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
