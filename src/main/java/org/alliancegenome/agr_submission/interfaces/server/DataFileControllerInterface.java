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

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/datafile")
@Api(value = "Data File Endpoints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DataFileControllerInterface {

	@POST
	@Secured
	@Path("/{schemaVersion}/{dataType}/{dataSubtype}")
	@ApiOperation("Create Entity")
	@JsonView(View.DataFileCreate.class)
	public DataFile create(
		@ApiParam(value = "SchemaVersion: data") @PathParam("schemaVersion") String schemaVersion,
		@ApiParam(value = "DataType: id") @PathParam("dataType") String dataType,
		@ApiParam(value = "DataSubType: id") @PathParam("dataSubtype") String dataSubtype,
		DataFile entity
	);
	
	@GET
	@Path("/{id}")
	@ApiOperation("Reads Entity")
	@JsonView(View.DataFileRead.class)
	public DataFile get(@ApiParam(value = "Read: id") @PathParam("id") Long id);
	
	@PUT
	@Secured
	@Path("/")
	@ApiOperation("Update Entity")
	@JsonView(View.DataFileUpdate.class)
	public DataFile update(@ApiParam(value = "Update: Entity") DataFile entity);
	
	@DELETE
	@Secured
	@Path("/{id}")
	@ApiOperation("Delete Entity")
	@JsonView(View.DataFileDelete.class)
	public DataFile delete(@ApiParam(value = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@ApiOperation("Get All Entities")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataFiles();
	
	@GET
	@Path("/{dataType}")
	@ApiOperation("Gets Data Files by Type")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataTypeFiles(
		@ApiParam(value = "DataType: id") @PathParam("dataType") String dataType
	);
	
	@GET
	@Path("/{dataType}/{dataSubtype}")
	@ApiOperation("Gets Data Files by Type and subtype")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataTypeSubTypeFiles(
		@ApiParam(value = "DataType: id") @PathParam("dataType") String dataType,
		@ApiParam(value = "DataSubType: id") @PathParam("dataSubtype") String dataSubtype
	);

}
