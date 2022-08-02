package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/schemaversion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Schema Version Endpoints")
public interface SchemaVersionControllerInterface {

	@POST @Secured
	@Path("/")
	@JsonView(View.SchemaVersionCreate.class)
	public SchemaVersion create(SchemaVersion entity);
	
	@GET
	@Path("/{id}")
	@JsonView(View.SchemaVersionRead.class)
	public SchemaVersion get(@PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.SchemaVersionUpdate.class)
	public SchemaVersion update(SchemaVersion entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.SchemaVersionDelete.class)
	public SchemaVersion delete(@PathParam("id") Long id);
	
	@GET
	@Path("/current")
	@JsonView(View.SchemaVersionView.class)
	public SchemaVersion getCurrentSchemaVersion();
	
	@GET
	@Path("/all")
	@JsonView(View.SchemaVersionView.class)
	public List<SchemaVersion> getSchemaVersions();

}