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
import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/releaseversion")
@Api(value = "Release Version Endpoints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ReleaseVersionControllerInterface {

	@POST @Secured
	@Path("/")
	@ApiOperation("Create Entity")
	@JsonView(View.ReleaseVersionCreate.class)
	public ReleaseVersion create(ReleaseVersion entity);
	
	@GET
	@Path("/{id}")
	@ApiOperation("Reads Entity")
	@JsonView(View.ReleaseVersionRead.class)
	public ReleaseVersion get(@ApiParam(value = "Read: id") @PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@ApiOperation("Update Entity")
	@JsonView(View.ReleaseVersionUpdate.class)
	public ReleaseVersion update(@ApiParam(value = "Update: Entity") ReleaseVersion entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@ApiOperation("Delete Entity")
	@JsonView(View.ReleaseVersionDelete.class)
	public ReleaseVersion delete(@ApiParam(value = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@ApiOperation("Get All Entities")
	@JsonView(View.ReleaseVersionView.class)
	public List<ReleaseVersion> getReleaseVersions();

	@GET
	@Path("/{release}/addschema/{schema}")
	@ApiOperation("Added Schema to Release")
	@JsonView(View.ReleaseVersionRead.class)
	public ReleaseVersion addSchema(
			@ApiParam(value = "Release") @PathParam("release") String release,
			@ApiParam(value = "Schema") @PathParam("schema") String schema
	);
	
}