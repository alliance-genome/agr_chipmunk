package org.alliancegenome.agr_submission.util.github;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.HttpStatusIOException;

@Path("/repos/alliance-genome/")
@Produces(MediaType.APPLICATION_JSON)
public interface GithubRESTAPIInterface {

	@GET
	@Path("/{repo}/releases/latest")
	public GithubRelease getLatestRelease(@PathParam("repo") String repo) throws HttpStatusIOException;

	@GET
	@Path("/{repo}/releases/tags/{release}")
	public GithubRelease getRelease(@PathParam("repo") String repo, @PathParam("release") String release) throws HttpStatusIOException;

	@GET
	@Path("/{repo}/releases")
	public List<GithubRelease> getReleases(@PathParam("repo") String repo) throws HttpStatusIOException;

}