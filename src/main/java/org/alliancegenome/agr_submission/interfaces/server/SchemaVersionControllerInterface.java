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
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/schemaversion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SchemaVersionControllerInterface {

	@POST @Secured
	@Path("/")
	@JsonView(View.SchemaVersionCreate.class)
	public SchemaVersion create(SchemaVersion entity);
	
	@GET
	@Path("/{id}")
	@JsonView(View.SchemaVersionRead.class)
	public SchemaVersion get(@Parameter(name = "Read: id") @PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.SchemaVersionUpdate.class)
	public SchemaVersion update(@Parameter(name = "Update: Entity") SchemaVersion entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.SchemaVersionDelete.class)
	public SchemaVersion delete(@Parameter(name = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@JsonView(View.SchemaVersionView.class)
	public List<SchemaVersion> getSchemaVersions();

}