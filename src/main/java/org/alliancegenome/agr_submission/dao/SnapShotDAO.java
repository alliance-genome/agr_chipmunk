package org.alliancegenome.agr_submission.dao;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.SnapShot;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SnapShotDAO extends BaseSQLDAO<SnapShot> {

	public SnapShotDAO() {
		super(SnapShot.class);
	}

}
