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
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/datasubtype")
@Api(value = "Data SubType Endpoints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DataSubTypeControllerInterface {

	@POST
	@Secured
	@Path("/")
	@ApiOperation("Create Entity")
	@JsonView(View.DataSubTypeCreate.class)
	public DataSubType create(DataSubType entity);
	
	@GET
	@Path("/{id}")
	@ApiOperation("Reads Entity")
	@JsonView(View.DataSubTypeRead.class)
	public DataSubType get(@ApiParam(value = "Read: id") @PathParam("id") Long id);
	
	@PUT
	@Secured
	@Path("/")
	@ApiOperation("Update Entity")
	@JsonView(View.DataSubTypeUpdate.class)
	public DataSubType update(@ApiParam(value = "Update: Entity") DataSubType entity);
	
	@DELETE
	@Secured
	@Path("/{id}")
	@ApiOperation("Delete Entity")
	@JsonView(View.DataSubTypeDelete.class)
	public DataSubType delete(@ApiParam(value = "Delete: Entity") @PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@ApiOperation("Get All Entities")
	@JsonView(View.DataSubTypeView.class)
	public List<DataSubType> getDataSubTypes();

}