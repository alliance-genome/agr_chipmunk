package org.alliancegenome.agr_submission.dao;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;

@ApplicationScoped
public class ReleaseVersionDAO extends BaseSQLDAO<ReleaseVersion> {

	public ReleaseVersionDAO() {
		super(ReleaseVersion.class);
	}

}
