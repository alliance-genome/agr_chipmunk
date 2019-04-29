package org.alliancegenome.agr_submission.interfaces.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.responces.APIResponce;
import org.alliancegenome.agr_submission.responces.SnapShotResponce;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Submission Endpoints")
public interface SubmissionControllerInterface {
	
	@GET
	@Path("/snapshot")
	@JsonView({View.SnapShotView.class})
	public APIResponce getSnapShot(@QueryParam(value = "releaseVersion") String releaseVersion);
	
	@GET
	@Path("/takesnapshot")
	@JsonView({View.SnapShotView.class})
	@Secured @SecurityRequirement(name = "api_token", scopes = "write: read")
	@APIResponses({
		@APIResponse(responseCode = "200", description = "The SnapShot", content = @Content(schema = @Schema(implementation = SnapShotResponce.class))),
		@APIResponse(responseCode = "400", description = "Unable to take snapshot")
	})
	@Operation(summary = "Creates a new SnapShot", description="This endpoint is used for creating a new SnapShot")
	public SnapShotResponce takeSnapShot(@QueryParam(value = "releaseVersion") String releaseVersion);

	@GET
	@Path("/releases")
	@JsonView({View.ReleaseVersionView.class})
	public APIResponce getReleases();
	
	@POST
	@Path("/submit")
	@Secured @SecurityRequirement(name = "api_token", scopes = "write: read")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@JsonView({View.API.class})
	public APIResponce submitData(MultipartFormDataInput input);

	@POST
	@Path("/validate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@JsonView({View.API.class})
	public APIResponce validateData(MultipartFormDataInput input);

}
