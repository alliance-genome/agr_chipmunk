package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/datafile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "DataFile Endpoints")
public interface DataFileControllerInterface {

	@POST @Secured
	@Path("/{releaseVersion}/{dataType}/{dataSubtype}")
	@JsonView(View.DataFileCreate.class)
	public DataFile create(
			@PathParam("releaseVersion") String releaseVersion,
			@PathParam("dataType") String dataType,
			@PathParam("dataSubtype") String dataSubtype,
			DataFile entity
			);

	@GET
	@Path("/{id}")
	@JsonView(View.DataFileRead.class)
	public DataFile get(
		@Parameter(in=ParameterIn.PATH, name="id", description = "Long Id or md5Sum", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("id") String id
	);

	@PUT @Secured
	@Path("/")
	@JsonView(View.DataFileUpdate.class)
	public DataFile update(DataFile entity);

	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.DataFileDelete.class)
	public DataFile delete(@PathParam("id") Long id);

	@GET
	@Path("/all")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataFiles();

	@GET
	@Path("/by/{dataType}")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataTypeFiles(
		@PathParam("dataType") String dataType
	);
	
	@GET
	@Path("/by/release/{releaseVersion}")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataFilesByRelease(
		@PathParam("releaseVersion") String releaseVersion,
		@DefaultValue("false")
		@Parameter(in=ParameterIn.QUERY, name="latest", description = "Latest File or All", required=false, schema = @Schema(type = SchemaType.BOOLEAN)) @QueryParam("latest") Boolean latest
	);
	
	@GET
	@Path("/by/{dataType}/{dataSubtype}")
	@JsonView(View.DataFileView.class)
	@Operation(summary = "Get list of DataFile's", description = "Get list of DataFile's")
	public List<DataFile> getDataTypeSubTypeFiles(
		@Parameter(in=ParameterIn.PATH, name="dataType", description = "Data Type Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataType") String dataType,
		@Parameter(in=ParameterIn.PATH, name="dataSubtype", description = "Data Sub Type Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataSubtype") String dataSubType,
		@DefaultValue("false")
		@Parameter(in=ParameterIn.QUERY, name="latest", description = "Latest File or All", required=false, schema = @Schema(type = SchemaType.BOOLEAN)) @QueryParam("latest") Boolean latest
	);

	@GET
	@Path("/by/{releaseVersion}/{dataType}/{dataSubtype}")
	@JsonView(View.DataFileView.class)
	@Operation(summary = "Get list of DataFile's", description = "Get list of DataFile's")
	public List<DataFile> getReleaseDataTypeSubTypeFiles(
		@Parameter(in=ParameterIn.PATH, name="releaseVersion", description = "Release Version Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("releaseVersion") String releaseVersion,
		@Parameter(in=ParameterIn.PATH, name="dataType", description = "Data Type Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataType") String dataType,
		@Parameter(in=ParameterIn.PATH, name="dataSubtype", description = "Data Sub Type Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataSubtype") String dataSubType,
		@DefaultValue("false")
		@Parameter(in=ParameterIn.QUERY, name="latest", description = "Latest File or All", required=false, schema = @Schema(type = SchemaType.BOOLEAN)) @QueryParam("latest") Boolean latest
	);
}
