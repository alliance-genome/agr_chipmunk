package org.alliancegenome.agr_submission.interfaces.server;

import java.util.List;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.entities.LoggedInUser;
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

@Path("/api/user")
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
