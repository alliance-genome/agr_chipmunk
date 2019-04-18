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
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/snapshot")
@Api(value = "Snap Shot Endpoints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SnapShotControllerInterface {

	@POST @Secured
	@Path("/")
	@ApiOperation("Create Entity")
	@JsonView(View.SnapShotCreate.class)
	public SnapShot create(SnapShot entity);
	
	@GET
	@Path("/{id}")
	@ApiOperation("Reads Entity")
	@JsonView(View.SnapShotRead.class)
	public SnapShot get(@ApiParam(value = "Read: id") @PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@ApiOperation("Update Entity")
	@JsonView(View.SnapShotUpdate.class)
	public SnapShot update(@ApiParam(value = "Update: Entity") SnapShot entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@ApiOperation("Delete Entity")
	@JsonView(View.SnapShotDelete.class)
	public SnapShot delete(@ApiParam(value = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@ApiOperation("Get All Entities")
	@JsonView(View.SnapShotView.class)
	public List<SnapShot> getSnapShots();

}