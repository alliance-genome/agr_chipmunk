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
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/datafile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DataFileControllerInterface {

	@POST
	@Secured
	@Path("/{schemaVersion}/{dataType}/{dataSubtype}")
	@JsonView(View.DataFileCreate.class)
	public DataFile create(
		@Parameter(name = "SchemaVersion: data") @PathParam("schemaVersion") String schemaVersion,
		@Parameter(name = "DataType: id") @PathParam("dataType") String dataType,
		@Parameter(name = "DataSubType: id") @PathParam("dataSubtype") String dataSubtype,
		DataFile entity
	);
	
	@GET
	@Path("/{id}")
	@JsonView(View.DataFileRead.class)
	public DataFile get(@Parameter(name = "Read: id") @PathParam("id") Long id);
	
	@PUT
	@Secured
	@Path("/")
	@JsonView(View.DataFileUpdate.class)
	public DataFile update(@Parameter(name = "Update: Entity") DataFile entity);
	
	@DELETE
	@Secured
	@Path("/{id}")
	@JsonView(View.DataFileDelete.class)
	public DataFile delete(@Parameter(name = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataFiles();
	
	@GET
	@Path("/{dataType}")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataTypeFiles(
		@Parameter(name = "DataType: id") @PathParam("dataType") String dataType
	);
	
	@GET
	@Path("/{dataType}/{dataSubtype}")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataTypeSubTypeFiles(
		@Parameter(name = "DataType: id") @PathParam("dataType") String dataType,
		@Parameter(name = "DataSubType: id") @PathParam("dataSubtype") String dataSubtype
	);

}
