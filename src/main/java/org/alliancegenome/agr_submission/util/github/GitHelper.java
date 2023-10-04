package org.alliancegenome.agr_submission.util.github;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.TagOpt;

import io.quarkus.logging.Log;

public class GitHelper {

	private String localGitPath = "/tmp/git";

	public void setupSchemaRelease(String schemaRelease) {
		String localPath = localGitPath + "/" + schemaRelease;

		File gitRepoDir = new File(localPath);

		try {
			if(!gitRepoDir.exists() || !gitRepoDir.isDirectory()) {
				Log.debug("Cloning Repo: ");
				Git.cloneRepository()
					.setURI( "https://github.com/alliance-genome/agr_schemas.git" )
					.setDirectory(new File(localPath))
					.call();
			}

			Git git = Git.open(gitRepoDir);
			git.checkout().setName("master").call();

			Repository repo = git.getRepository();

			for(Ref key: repo.getRefDatabase().getRefsByPrefix(Constants.R_TAGS)) {
				git.tagDelete().setTags(key.getName()).call();
			}

			git.fetch().setTagOpt(TagOpt.FETCH_TAGS).call();
			//git.fetch().set
			git.pull().call();

			List<String> tags = new ArrayList<String>();
			List<Ref> list = git.tagList().call();
			list.stream().forEach(
				ref -> {
					tags.add(ref.getName().replace("refs/tags/", ""));
				}
			);
			//log.debug("Tags: " + tags);

			Map<String, String> remoteBranches = new HashMap<String, String>();
			List<Ref> list2 = git.branchList().setListMode(ListMode.ALL).call();
			list2.stream().forEach(
				ref -> {
					if(ref.getName().startsWith("refs/heads/release-")) {
						Log.debug("Deleting Local Branch: " + ref.getName());
						try {
							git.branchDelete().setBranchNames(ref.getName()).call();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						remoteBranches.put(ref.getName().replace("refs/remotes/origin/", ""), ref.getName());
					}
				}
			);
			//log.debug("Remote Branches: " + remoteBranches);


			if(tags.contains(schemaRelease)) {
				Log.debug("Checking out tag: " + schemaRelease);
				git.checkout().setName(schemaRelease).call();
				//git.checkout().setCreateBranch(true).setName(schemaRelease)
				//	.setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
				//	.setStartPoint("origin/" + schemaRelease).call();
			} else if(remoteBranches.containsKey("release-" + schemaRelease)) {
				Log.debug("Checking out remote branch: " + "release-" + schemaRelease);
				git.checkout().setName(remoteBranches.get("release-" + schemaRelease)).call();
			} else {
				// Do nothing
			}

		} catch(Exception e) {
			Log.error("Error Happened: " + e);
		}
	}

	public File getFile(String schemaRelease, String filePath) {
		setupSchemaRelease(schemaRelease);
		String path = localGitPath + "/" + schemaRelease;
		Log.debug("Validation File: " + path + filePath);
		return new File(path + filePath);
	}

}
