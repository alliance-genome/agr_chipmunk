package org.alliancegenome.agr_submission.services;

import java.util.*;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.*;
import org.alliancegenome.agr_submission.entities.*;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@RequestScoped
public class ReleaseVersionService extends BaseService<ReleaseVersion> {

	@Inject ReleaseVersionDAO dao;
	@Inject SchemaVersionDAO schemaDAO;

	@Override
	@Transactional
	public ReleaseVersion create(ReleaseVersion entity) {
		Log.info("ReleaseVersionService: create: ");
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public ReleaseVersion get(Long id) {
		return dao.find(id);
	}

	@Transactional
	public ReleaseVersion get(String id) {
		Log.info("ReleaseVersionService: get: " + id);
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
		Log.info("ReleaseVersionService: update: ");
		return dao.merge(entity);
	}

	@Override
	@Transactional
	public ReleaseVersion delete(Long id) {
		Log.info("ReleaseVersionService: delete: " + id);
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
	
	@Transactional
	public ReleaseVersion getCurrentRelease() {
		List<ReleaseVersion> releaseVersions = getReleaseVersions();
		ReleaseVersion nextRelease = null;
		Date now = new Date();
		for(ReleaseVersion rv: releaseVersions) {
			if(now.after(rv.getReleaseDate()) && (nextRelease == null || rv.getReleaseDate().after(nextRelease.getReleaseDate()))) {
				nextRelease = rv;
			}
		}
		return nextRelease;
	}

}
