package org.alliancegenome.agr_submission.services;

import java.util.List;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.DataSubTypeDAO;
import org.alliancegenome.agr_submission.entities.DataSubType;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@RequestScoped
public class DataSubTypeService extends BaseService<DataSubType> {

	@Inject DataSubTypeDAO dao;

	@Override
	@Transactional
	public DataSubType create(DataSubType entity) {
		log.info("DataSubTypeService: create: ");
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public DataSubType get(Long id) {
		log.info("DataSubTypeService: get: " + id);
		return dao.find(id);
	}

	@Override
	@Transactional
	public DataSubType update(DataSubType entity) {
		log.info("DataSubTypeService: update: ");
		return dao.merge(entity);
	}

	@Override
	@Transactional
	public DataSubType delete(Long id) {
		log.info("DataSubTypeService: delete: " + id);
		return dao.remove(id);
	}

	public List<DataSubType> getDataSubTypes() {
		return dao.findAll();
	}


}
