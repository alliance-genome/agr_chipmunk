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
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.SchemaFile;
import org.alliancegenome.agr_submission.forms.AddDataSubTypeForm;
import org.alliancegenome.agr_submission.forms.CreateSchemaFileForm;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/datatype")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Data Type Endpoints")
public interface DataTypeControllerInterface {

	@POST @Secured
	@Path("/")
	@JsonView(View.DataTypeCreate.class)
	public DataType create(DataType entity);
	
	@GET
	@Path("/{id}")
	@JsonView(View.DataTypeRead.class)
	public DataType get(@Parameter(name = "Read: id") @PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.DataTypeUpdate.class)
	public DataType update(@Parameter(name = "Update: Entity") DataType entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.DataTypeDelete.class)
	public DataType delete(@Parameter(name = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@JsonView(View.DataTypeView.class)
	public List<DataType> getDataTypes();
	
	@POST @Secured
	@Path("/{dataType}/addschemafile")
	@JsonView({View.DataTypeView.class})
	public DataType addSchemaFile(
			@Parameter(name = "DataType: id") @PathParam("dataType") String dataType,
			CreateSchemaFileForm form
	);
	
	@DELETE @Secured
	@Path("/{dataType}/removeschemafile/{id}")
	@JsonView(View.DataTypeDelete.class)
	public SchemaFile deleteSchemaFile(
			@Parameter(name = "DataType: id") @PathParam("dataType") String dataType,
			@Parameter(name = "SchemaFile: id") @PathParam("id") Long id);
	
	@POST @Secured
	@Path("/{dataType}/addsubtype")
	@JsonView({View.DataTypeView.class})
	public DataType addDataSubType(
		@Parameter(name = "DataType: id") @PathParam("dataType") String dataType,
		AddDataSubTypeForm form
	);

}
