package org.alliancegenome.agr_submission.services;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.DataSubTypeDAO;
import org.alliancegenome.agr_submission.dao.DataTypeDAO;
import org.alliancegenome.agr_submission.dao.SchemaFileDAO;
import org.alliancegenome.agr_submission.dao.SchemaVersionDAO;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.SchemaFile;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.forms.CreateSchemaFileForm;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class DataTypeService extends BaseService<DataType> {

	@Inject private DataTypeDAO dao;
	@Inject private DataSubTypeDAO subTypeDAO;
	@Inject private SchemaVersionDAO schemaDAO;
	@Inject private SchemaFileDAO schemaFileDAO;

	@Override
	@Transactional
	public DataType create(DataType entity) {
		log.info("DataTypeService: create: ");
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public DataType get(Long id) {
		log.info("DataTypeService: get: " + id);
		return dao.find(id);
	}

	@Override
	@Transactional
	public DataType update(DataType entity) {
		log.info("DataTypeService: update: ");
		DataType dbEntity = get(entity.getId());
		dbEntity.setDataSubTypeRequired(entity.isDataSubTypeRequired());
		dbEntity.setDescription(entity.getDescription());
		dbEntity.setFileExtension(entity.getFileExtension());
		dbEntity.setName(entity.getName());
		dbEntity.setValidationRequired(entity.isValidationRequired());
		return dao.merge(dbEntity);
	}

	@Override
	@Transactional
	public DataType delete(Long id) {
		log.info("DataTypeService: delete: " + id);
		return dao.remove(id);
	}

	public List<DataType> getDataTypes() {
		return dao.findAll();
	}

	@Transactional
	public DataType addSchemaFile(String dataType, CreateSchemaFileForm form) {
		DataType type = dao.findByField("name", dataType);
		SchemaVersion sv = schemaDAO.findByField("schema", form.getSchema());
		if(type != null && sv != null && !type.getSchemaFilesMap().containsKey(form.getSchema())) {
			SchemaFile file = new SchemaFile();
			file.setDataType(type);
			file.setSchemaVersion(sv);
			file.setFilePath(form.getFilePath());
			schemaFileDAO.persist(file);
			type.getSchemaFiles().add(file);
			return type;
		} else {
			log.error("Failed adding Schema File: " + type + " " + sv + " " + type.getSchemaFilesMap());
			return null;
		}
	}
	
	@Transactional
	public DataType addDataSubType(String dataType, String dataSubType) {
		DataType type = dao.findByField("name", dataType);
		DataSubType subType = subTypeDAO.findByField("name", dataSubType);
		if(type != null && subType != null) {
			type.getDataSubTypes().add(subType);
			dao.persist(type);
		}
		return type;
	}

	public SchemaFile deleteSchemaFile(String dataType, Long id) {
		return schemaFileDAO.remove(id);
	}

}
