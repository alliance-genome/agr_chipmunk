package org.alliancegenome.agr_submission.services;

import java.util.List;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.SchemaFileDAO;
import org.alliancegenome.agr_submission.entities.SchemaFile;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@RequestScoped
public class SchemaFileService extends BaseService<SchemaFile> {

	@Inject SchemaFileDAO dao;

	@Override
	@Transactional
	public SchemaFile create(SchemaFile entity) {
		log.info("DataSubTypeService: create: ");
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public SchemaFile get(Long id) {
		log.info("DataSubTypeService: get: " + id);
		return dao.find(id);
	}

	@Override
	@Transactional
	public SchemaFile update(SchemaFile entity) {
		SchemaFile f = get(entity.getId());
		f.setFilePath(entity.getFilePath());
		log.info("DataSubTypeService: update: ");
		return dao.merge(f);
	}

	@Override
	@Transactional
	public SchemaFile delete(Long id) {
		log.info("DataSubTypeService: delete: " + id);
		return dao.remove(id);
	}

	public List<SchemaFile> getSchemaFiles() {
		return dao.findAll();
	}


}
