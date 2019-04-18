package org.alliancegenome.agr_submission.services;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.SnapShotDAO;
import org.alliancegenome.agr_submission.entities.SnapShot;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class SnapShotService extends BaseService<SnapShot> {

	@Inject
	private SnapShotDAO dao;

	@Override
	@Transactional
	public SnapShot create(SnapShot entity) {
		log.info("SnapShotService: create: ");
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public SnapShot get(Long id) {
		log.info("SnapShotService: get: " + id);
		return dao.find(id);
	}

	@Override
	@Transactional
	public SnapShot update(SnapShot entity) {
		log.info("SnapShotService: update: ");
		return dao.merge(entity);
	}

	@Override
	@Transactional
	public SnapShot delete(Long id) {
		log.info("SnapShotService: delete: " + id);
		return dao.remove(id);
	}

	public List<SnapShot> getSnapShots() {
		return dao.findAll();
	}


}
