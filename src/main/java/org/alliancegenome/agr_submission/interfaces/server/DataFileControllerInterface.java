package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/api/datafile")
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

	@PUT @Secured
	@Path("/{id}/validateToggle")
	@JsonView(View.DataFileUpdate.class)
	public DataFile validateToggle(
		@Parameter(in=ParameterIn.PATH, name="id", description = "Long Id or md5Sum", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("id") String id
	);

	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.DataFileDelete.class)
	public DataFile delete(@PathParam("id") Long id);

	@GET
	@Path("/all")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataFiles();

//	@GET
//	@Path("/diff/{id1}/{id2}")
//	@JsonView(View.DataFileView.class)
//	@APIResponse(
//			responseCode = "200",
//			description = "Json Patch to show the difference between two files",
//			content = @Content(
//					schema = @Schema(
//							type = SchemaType.OBJECT,
//							implementation = JsonPatch.class)))
//	public JsonPatch diffDataFiles(
//		@Parameter(in=ParameterIn.PATH, name="id1", description = "Long Id or md5Sum", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("id1") String id1,
//		@Parameter(in=ParameterIn.PATH, name="id2", description = "Long Id or md5Sum", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("id2") String id2
//	);

	@POST @Secured
	@Path("/change_datatype/{id}/{dataType}")
	@JsonView(View.DataFileCreate.class)
	public DataFile changeDataType(
		@Parameter(in=ParameterIn.PATH, name="id", description = "DataFile Id", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("id") String id,
		@Parameter(in=ParameterIn.PATH, name="dataType", description = "New Data Type by Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataType") String dataType
	);

	@POST @Secured
	@Path("/change_datasubtype/{id}/{dataSubType}")
	@JsonView(View.DataFileCreate.class)
	public DataFile changeDataSubType(
		@Parameter(in=ParameterIn.PATH, name="id", description = "DataFile Id", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("id") String id,
		@Parameter(in=ParameterIn.PATH, name="dataSubType", description = "New Data SubType by Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataSubType") String dataSubType
	);

	@POST @Secured
	@Path("/change/{id}/{dataType}/{dataSubType}")
	@JsonView(View.DataFileCreate.class)
	public DataFile changeDataTypeAndDataSubType(
		@Parameter(in=ParameterIn.PATH, name="id", description = "DataFile Id", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("id") String id,
		@Parameter(in=ParameterIn.PATH, name="dataType", description = "New Data Type by Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataType") String dataType,
		@Parameter(in=ParameterIn.PATH, name="dataSubType", description = "New Data SubType by Name", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("dataSubType") String dataSubType
	);

	@POST @Secured
	@Path("/assign/{releaseVersion1}/{releaseVersion2}")
	@JsonView(View.DataFileCreate.class)
	public List<DataFile> assignDataFilesFromRelease1ToRelease2(
		@Parameter(in=ParameterIn.PATH, name="releaseVersion1", description = "Old Release Version", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("releaseVersion1") String releaseVersion1,
		@Parameter(in=ParameterIn.PATH, name="releaseVersion2", description = "New Release Version", required=true, schema = @Schema(type = SchemaType.STRING)) @PathParam("releaseVersion2") String releaseVersion2
	);

	@GET
	@Path("/by/{dataType}")
	@JsonView(View.DataFileView.class)
	public List<DataFile> getDataTypeFiles(
		@PathParam("dataType") String dataType,
		@DefaultValue("false")
		@Parameter(in=ParameterIn.QUERY, name="latest", description = "Latest File or All", required=false, schema = @Schema(type = SchemaType.BOOLEAN)) @QueryParam("latest") Boolean latest
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
