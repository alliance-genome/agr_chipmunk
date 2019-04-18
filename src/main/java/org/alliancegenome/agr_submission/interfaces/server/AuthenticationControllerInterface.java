package org.alliancegenome.agr_submission.interfaces.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.alliancegenome.agr_submission.auth.Credentials;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/authentication")
@Api(value = "User Authentication Endpoints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthenticationControllerInterface {

	@POST
	@ApiOperation("Authenticate User")
	public Response authenticateUser(@ApiParam(value = "User Credentials") Credentials creds);
}
