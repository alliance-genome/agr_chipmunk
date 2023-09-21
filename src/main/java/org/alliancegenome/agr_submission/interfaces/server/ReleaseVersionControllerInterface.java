package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.*;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/releaseversion")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Release Version Endpoints")
public interface ReleaseVersionControllerInterface {

	@POST @Secured
	@Path("/")
	@JsonView(View.ReleaseVersionCreate.class)
	public ReleaseVersion create(ReleaseVersion entity);
	
	@GET
	@Path("/{id}")
	@JsonView(View.ReleaseVersionRead.class)
	public ReleaseVersion get(
			@Parameter(in=ParameterIn.PATH, name="id", description = "Long Id or Release Number", required=true, schema = @Schema(type = SchemaType.STRING)) 
			@PathParam("id") String id);
	
	@GET
	@Path("/{id}/snapshots")
	@JsonView(View.ReleaseVersionRead.class)
	public List<SnapShot> getSnapshots(
			@Parameter(in=ParameterIn.PATH, name="id", description = "Long Id or Release Number", required=true, schema = @Schema(type = SchemaType.STRING)) 
			@PathParam("id") String id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.ReleaseVersionUpdate.class)
	public ReleaseVersion update(ReleaseVersion entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.ReleaseVersionDelete.class)
	public ReleaseVersion delete(@PathParam("id") Long id);
	
	@GET
	@Path("/current")
	@JsonView(View.ReleaseVersionView.class)
	public ReleaseVersion getCurrentRelease();
	
	@GET
	@Path("/next")
	@JsonView(View.ReleaseVersionView.class)
	public ReleaseVersion getNextRelease();
	
	@GET
	@Path("/all")
	@JsonView(View.ReleaseVersionView.class)
	public List<ReleaseVersion> getReleaseVersions();

	@GET @Secured
	@Path("/{release}/setschema/{schema}")
	@JsonView(View.ReleaseVersionRead.class)
	public ReleaseVersion setSchema(
			@PathParam("release") String release,
			@PathParam("schema") String schema
	);
	
}