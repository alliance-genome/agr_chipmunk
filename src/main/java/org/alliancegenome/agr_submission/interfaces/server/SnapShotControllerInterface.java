package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.SnapShot;
import org.alliancegenome.agr_submission.responces.APIResponce;
import org.alliancegenome.agr_submission.responces.SnapShotResponce;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/snapshot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "SnapShot Endpoints")
public interface SnapShotControllerInterface {

	@GET
	@Path("/release/{releaseVersion}")
	@JsonView({View.SnapShotView.class})
	public APIResponce getSnapShot(@PathParam(value = "releaseVersion") String releaseVersion);
	
	@GET
	@Path("/take/{releaseVersion}")
	@JsonView({View.SnapShotView.class})
	@Secured @SecurityRequirement(name = "api_token", scopes = "write: read")
	@APIResponses({
		@APIResponse(responseCode = "200", description = "The SnapShot", content = @Content(schema = @Schema(implementation = SnapShotResponce.class))),
		@APIResponse(responseCode = "400", description = "Unable to take snapshot")
	})
	@Operation(summary = "Creates a new SnapShot", description="This endpoint is used for creating a new SnapShot")
	public SnapShotResponce takeSnapShot(@PathParam(value = "releaseVersion") String releaseVersion);
	
	@POST
	@Secured @SecurityRequirement(name = "api_token", scopes = "write: read")
	@Path("/")
	@APIResponses({
		@APIResponse(responseCode = "200", description = "The SnapShot", content = @Content(schema = @Schema(implementation = SnapShot.class))),
		@APIResponse(responseCode = "400", description = "User not found")
	})
	@Operation(summary = "Creates a new SnapShot", description="This endpoint is used for creating a new SnapShot")
	@JsonView(View.SnapShotCreate.class)
	public SnapShot create(SnapShot entity);

	@GET
	@Path("/{id}")
	@JsonView(View.SnapShotRead.class)
	public SnapShot get(@Parameter(name = "Read: id") @PathParam("id") Long id);

	@PUT
	@Secured @SecurityRequirement(name = "api_token", scopes = "write: read")
	@Path("/")
	@JsonView(View.SnapShotUpdate.class)
	public SnapShot update(@Parameter(name = "Update: Entity") SnapShot entity);

	@DELETE
	@Secured @SecurityRequirement(name = "api_token", scopes = "write: read")
	@Path("/{id}")
	@JsonView(View.SnapShotDelete.class)
	public SnapShot delete(@Parameter(name = "Delete: Entity") @PathParam("id") Long id);

	@GET
	@Path("/all")
	@JsonView(View.SnapShotView.class)
	public List<SnapShot> getSnapShots();

}