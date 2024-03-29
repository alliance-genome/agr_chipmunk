package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.SchemaFile;
import org.alliancegenome.agr_submission.forms.AddDataSubTypeForm;
import org.alliancegenome.agr_submission.forms.CreateSchemaFileForm;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/datatype")
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
	public DataType get(@PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.DataTypeUpdate.class)
	public DataType update(DataType entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.DataTypeDelete.class)
	public DataType delete(@PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@JsonView(View.DataTypeView.class)
	public List<DataType> getDataTypes();
	
	@POST @Secured
	@Path("/{dataType}/addschemafile")
	@JsonView({View.DataTypeView.class})
	public DataType addSchemaFile(
			@PathParam("dataType") String dataType,
			CreateSchemaFileForm form
	);
	
	@DELETE @Secured
	@Path("/{dataType}/removeschemafile/{id}")
	@JsonView(View.DataTypeDelete.class)
	public SchemaFile deleteSchemaFile(
		@PathParam("dataType") String dataType,
		@PathParam("id") Long id
	);
	
	@POST @Secured
	@Path("/{dataType}/addsubtype")
	@JsonView({View.DataTypeView.class})
	public DataType addDataSubType(
		@PathParam("dataType") String dataType,
		AddDataSubTypeForm form
	);

}
