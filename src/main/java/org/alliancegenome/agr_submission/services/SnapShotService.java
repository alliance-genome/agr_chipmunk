package org.alliancegenome.agr_submission.services;

import java.util.Date;
import java.util.List;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.ReleaseVersionDAO;
import org.alliancegenome.agr_submission.dao.SnapShotDAO;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.entities.SnapShot;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@RequestScoped
public class SnapShotService extends BaseService<SnapShot> {

	@Inject SnapShotDAO dao;
	@Inject ReleaseVersionDAO releaseDAO;

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

	@Transactional
	public List<SnapShot> getSnapShots() {
		return dao.findAll();
	}
	
	@Transactional
	public SnapShot getLatestShapShot(String releaseVersion) {
		ReleaseVersion rv = releaseDAO.findByField("releaseVersion", releaseVersion);
		if(rv != null) {
			SnapShot latest = null;
			log.debug("Snapshots under releases: " + rv.getSnapShots());
			for(SnapShot s: rv.getSnapShots()) {
				if(latest == null || latest.getSnapShotDate().before(s.getSnapShotDate())) {
					latest = s;
				}
			}
			latest.getDataFiles();
			return latest;
		}
		return null;
	}

	@Transactional
	public SnapShot takeSnapShot(String releaseVersion) {
		SnapShot s = new SnapShot();
		
		ReleaseVersion rv = releaseDAO.findByField("releaseVersion", releaseVersion);
		if(rv != null) {
			s.setReleaseVersion(rv);
			s.setSnapShotDate(new Date());
			
			SnapShot ret = dao.persist(s);
			ret.getDataFiles();
			return ret;
		}
		return null;
	}


}
