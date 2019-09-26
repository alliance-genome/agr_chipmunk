package org.alliancegenome.agr_submission.services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.ReleaseVersionDAO;
import org.alliancegenome.agr_submission.dao.SchemaVersionDAO;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.entities.SnapShot;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class ReleaseVersionService extends BaseService<ReleaseVersion> {

	@Inject private ReleaseVersionDAO dao;
	@Inject private SchemaVersionDAO schemaDAO;

	@Override
	@Transactional
	public ReleaseVersion create(ReleaseVersion entity) {
		log.info("ReleaseVersionService: create: ");
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public ReleaseVersion get(Long id) {
		return dao.find(id);
	}

	@Transactional
	public ReleaseVersion get(String id) {
		log.info("ReleaseVersionService: get: " + id);
		try {
			Long ident = Long.parseLong(id);
			return get(ident);
		} catch (NumberFormatException ex) {
			return dao.findByField("releaseVersion", id);
		}
	}
	
	@Transactional
	public List<SnapShot> getSnapshots(String id) {
		List<SnapShot> ret = get(id).getSnapShots();
		ret.size();
		return ret;
	}

	@Override
	@Transactional
	public ReleaseVersion update(ReleaseVersion entity) {
		log.info("ReleaseVersionService: update: ");
		return dao.merge(entity);
	}

	@Override
	@Transactional
	public ReleaseVersion delete(Long id) {
		log.info("ReleaseVersionService: delete: " + id);
		return dao.remove(id);
	}

	public List<ReleaseVersion> getReleaseVersions() {
		return dao.findAll();
	}
	
	@Transactional
	public ReleaseVersion setSchema(String release, String schema) {
		ReleaseVersion rv = dao.findByField("releaseVersion", release);
		SchemaVersion sv = schemaDAO.findByField("schema", schema);
		if(rv != null && sv != null) {
			rv.setDefaultSchemaVersion(sv);
			dao.persist(rv);
		}
		return rv;
	}

	@Transactional
	public ReleaseVersion getNextRelease() {
		List<ReleaseVersion> releaseVersions = getReleaseVersions();
		ReleaseVersion nextRelease = null;
		Date now = new Date();
		for(ReleaseVersion rv: releaseVersions) {
			if(now.before(rv.getReleaseDate()) && (nextRelease == null || rv.getReleaseDate().before(nextRelease.getReleaseDate()))) {
				nextRelease = rv;
			}
		}
		return nextRelease;
	}

}
