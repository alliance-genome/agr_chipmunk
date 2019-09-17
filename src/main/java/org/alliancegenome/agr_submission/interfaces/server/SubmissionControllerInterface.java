package org.alliancegenome.agr_submission.interfaces.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.alliancegenome.agr_submission.auth.Secured;
import org.alliancegenome.agr_submission.responces.APIResponce;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Submission Endpoints")
public interface SubmissionControllerInterface {
	
	@POST
	@Path("/submit")
	@Secured
	//@SecurityRequirement(name = "api_token", scopes = "write: read")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@JsonView({View.API.class})
	public APIResponce submitData(MultipartFormDataInput input);

	@POST
	@Path("/validate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@JsonView({View.API.class})
	public APIResponce validateData(MultipartFormDataInput input);

}
