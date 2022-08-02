package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.SnapShot;
import org.alliancegenome.agr_submission.responces.*;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.*;
import org.eclipse.microprofile.openapi.annotations.responses.*;
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
	
	@GET @Secured
	@Path("/take/{releaseVersion}")
	@JsonView({View.SnapShotView.class})
	
	@APIResponses({
		@APIResponse(responseCode = "200", description = "The SnapShot", content = @Content(schema = @Schema(implementation = SnapShotResponce.class))),
		@APIResponse(responseCode = "400", description = "Unable to take snapshot")
	})
	@Operation(summary = "Creates a new SnapShot", description="This endpoint is used for creating a new SnapShot")
	public SnapShotResponce takeSnapShot(@PathParam(value = "releaseVersion") String releaseVersion);
	
	@POST @Secured
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
	public SnapShot get(@PathParam("id") Long id);

	@PUT @Secured
	@Path("/")
	@JsonView(View.SnapShotUpdate.class)
	public SnapShot update(SnapShot entity);

	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.SnapShotDelete.class)
	public SnapShot delete(@PathParam("id") Long id);

	@GET
	@Path("/all")
	@JsonView(View.SnapShotMultipleView.class)
	public List<SnapShot> getSnapShots();

}