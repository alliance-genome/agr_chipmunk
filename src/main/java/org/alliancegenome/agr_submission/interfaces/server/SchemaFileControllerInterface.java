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
import org.alliancegenome.agr_submission.entities.SchemaFile;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/schemafile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Schema File Endpoints")
public interface SchemaFileControllerInterface {

	@POST
	@Secured
	@Path("/")
	@JsonView(View.SchemaFileCreate.class)
	public SchemaFile create(SchemaFile entity);
	
	@GET
	@Path("/{id}")
	@JsonView(View.SchemaFileRead.class)
	public SchemaFile get(@Parameter(name = "Read: id") @PathParam("id") Long id);
	
	@PUT
	@Secured
	@Path("/")
	@JsonView(View.SchemaFileUpdate.class)
	public SchemaFile update(SchemaFile entity);
	
	@DELETE
	@Secured
	@Path("/{id}")
	@JsonView(View.SchemaFileDelete.class)
	public SchemaFile delete(@Parameter(name = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@JsonView(View.SchemaFileView.class)
	public List<SchemaFile> getSchemaFiles();

}