package org.alliancegenome.agr_submission.interfaces.server;

import org.alliancegenome.agr_submission.auth.Credentials;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/api/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication Endpoints")
public interface AuthenticationControllerInterface {

	@POST
	public Response authenticateUser(Credentials creds);
}
