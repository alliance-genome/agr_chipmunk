package org.alliancegenome.agr_submission.services;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.SchemaVersionDAO;
import org.alliancegenome.agr_submission.entities.SchemaVersion;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class SchemaVersionService extends BaseService<SchemaVersion> {

	@Inject
	private SchemaVersionDAO dao;

	@Override
	@Transactional
	public SchemaVersion create(SchemaVersion entity) {
		log.info("SchemaVersionService: create: ");
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public SchemaVersion get(Long id) {
		log.info("SchemaVersionService: get: " + id);
		return dao.find(id);
	}

	@Override
	@Transactional
	public SchemaVersion update(SchemaVersion entity) {
		log.info("SchemaVersionService: update: ");
		return dao.merge(entity);
	}

	@Override
	@Transactional
	public SchemaVersion delete(Long id) {
		log.info("SchemaVersionService: delete: " + id);
		return dao.remove(id);
	}

	public List<SchemaVersion> getSchemaVersions() {
		return dao.findAll();
	}


}
