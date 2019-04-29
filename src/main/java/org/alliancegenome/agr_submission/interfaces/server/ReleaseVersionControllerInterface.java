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
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/releaseversion")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ReleaseVersionControllerInterface {

	@POST @Secured
	@Path("/")
	@JsonView(View.ReleaseVersionCreate.class)
	public ReleaseVersion create(ReleaseVersion entity);
	
	@GET
	@Path("/{id}")
	@JsonView(View.ReleaseVersionRead.class)
	public ReleaseVersion get(@Parameter(name = "Read: id") @PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.ReleaseVersionUpdate.class)
	public ReleaseVersion update(@Parameter(name = "Update: Entity") ReleaseVersion entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.ReleaseVersionDelete.class)
	public ReleaseVersion delete(@Parameter(name = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@JsonView(View.ReleaseVersionView.class)
	public List<ReleaseVersion> getReleaseVersions();

	@GET
	@Path("/{release}/addschema/{schema}")
	@JsonView(View.ReleaseVersionRead.class)
	public ReleaseVersion addSchema(
			@Parameter(name = "Release") @PathParam("release") String release,
			@Parameter(name = "Schema") @PathParam("schema") String schema
	);
	
}