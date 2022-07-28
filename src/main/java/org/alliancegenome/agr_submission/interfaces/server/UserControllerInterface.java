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
import org.alliancegenome.agr_submission.entities.LoggedInUser;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User Endpoints")
public interface UserControllerInterface {

	@POST @Secured
	@Path("/")
	@JsonView(View.UserCreate.class)
	public LoggedInUser create(LoggedInUser entity);
	
	@GET @Secured
	@Path("/{id}")
	@JsonView(View.UserRead.class)
	public LoggedInUser get(@PathParam("id") Long id);
	
	@PUT @Secured
	@Path("/")
	@JsonView(View.UserUpdate.class)
	public LoggedInUser update(LoggedInUser entity);
	
	@DELETE @Secured
	@Path("/{id}")
	@JsonView(View.UserDelete.class)
	public LoggedInUser delete(@PathParam("id") Long id);
	
	@GET @Secured
	@Path("/all")
	@JsonView(View.UserView.class)
	public List<LoggedInUser> getUsers();
}
