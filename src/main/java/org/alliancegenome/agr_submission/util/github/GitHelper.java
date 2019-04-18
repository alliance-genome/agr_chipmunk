package org.alliancegenome.agr_submission.util.github;

import java.io.File;

import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class GitHelper {

	private Log log = LogFactory.getLog(getClass());
	private String prefix = "/git";

	public void setupRelease(String release) {
		String localPath = ConfigHelper.getValidationSoftwarePath() + prefix + "/" + release;

		File f = new File(localPath);

		if(f.exists() && f.isDirectory()) {
			log.debug("Directory already exists: " + localPath);
		} else {
			try {
				Git.cloneRepository()
						.setURI( "https://github.com/alliance-genome/agr_schemas.git" )
						.setDirectory(new File(localPath))
						.setBranch(release)
						.call();
			} catch (GitAPIException e) {
				e.printStackTrace();
			}
		}
	}

	public File getFile(String release, String filePath) {
		setupRelease(release);
		String path = ConfigHelper.getValidationSoftwarePath() + prefix + "/" + release;
		log.debug("Validation File: " + path + filePath);
		return new File(path + filePath);
	}

}
