package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/datasubtype")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Data Sub Type Endpoints")
public interface DataSubTypeControllerInterface {

	@POST @Secured
	@Path("/")
	@JsonView(View.DataSubTypeCreate.class)
	public DataSubType create(DataSubType entity);
	
	@GET
	@Path("/{id}")
	@JsonView(View.DataSubTypeRead.class)
	public DataSubType get(@PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.DataSubTypeUpdate.class)
	public DataSubType update(DataSubType entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.DataSubTypeDelete.class)
	public DataSubType delete(@PathParam("id") Long id);
	
	@GET
	@Path("/all")
	@JsonView(View.DataSubTypeView.class)
	public List<DataSubType> getDataSubTypes();

}