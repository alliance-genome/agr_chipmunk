package org.alliancegenome.agr_submission.dao;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.SnapShot;

@ApplicationScoped
public class SnapShotDAO extends BaseSQLDAO<SnapShot> {

	public SnapShotDAO() {
		super(SnapShot.class);
	}

}
