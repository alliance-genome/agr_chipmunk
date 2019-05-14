package org.alliancegenome.agr_submission.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.DataFileDAO;
import org.alliancegenome.agr_submission.dao.DataSubTypeDAO;
import org.alliancegenome.agr_submission.dao.DataTypeDAO;
import org.alliancegenome.agr_submission.dao.SchemaVersionDAO;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.SchemaVersion;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class DataFileService extends BaseService<DataFile> {

	@Inject private DataFileDAO dao;
	@Inject private DataTypeDAO dataTypeDAO;
	@Inject private DataSubTypeDAO dataSubTypeDAO;
	@Inject private SchemaVersionDAO schemaDAO;

	@Override
	public DataFile create(DataFile entity) {
		try {
			throw new Exception("Unimplemnted Error: Please use create(String schemaVersion, String dataType, String dataSubtype, DataFile entity)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public DataFile create(String schemaVersion, String dataType, String dataSubtype, DataFile entity) {
		log.info("DataFileService: create: ");
		DataType type = dataTypeDAO.findByField("name", dataType);
		DataSubType dataSubType = dataSubTypeDAO.findByField("name", dataSubtype);
		SchemaVersion sv = schemaDAO.findByField("schema", schemaVersion);
		if(type == null || dataSubType == null || sv == null) {
			log.error("Was not able to find everything needed to create data file: " + type + " " + dataSubType + " " + sv);
			return null;
		}
		entity.setDataSubType(dataSubType);
		entity.setSchemaVersion(sv);
		entity.setDataType(type);
		return dao.persist(entity);
	}
	
	@Override
	@Transactional
	public DataFile get(Long id) {
		return dao.find(id);
	}
	
	@Transactional
	public DataFile get(String id) {
		log.info("DataFileService: get: " + id);
		try {
			Long ident = Long.parseLong(id);
			return get(ident);
		} catch (NumberFormatException ex) {
			return dao.findByField("md5Sum", id);
		}
	}

	@Override
	@Transactional
	public DataFile update(DataFile entity) {
		log.info("DataFileService: update: ");
		return dao.merge(entity);
	}

	@Override
	@Transactional
	public DataFile delete(Long id) {
		log.info("DataFileService: delete: " + id);
		return dao.remove(id);
	}


	public List<DataFile> getDataFiles() {
		return dao.findAll();
	}

	@Transactional
	public List<DataFile> getDataTypeFiles(String dataType) {
		DataType type = dataTypeDAO.findByField("name", dataType);
		Map<String, Object> params = new HashMap<>();
		params.put("dataType.id", type.getId().toString());
		return dao.search(params, "uploadDate");
	}

	@Transactional
	public List<DataFile> getDataTypeSubTypeFiles(String dataType, String dataSubtype, Boolean latest) {
		DataType type = dataTypeDAO.findByField("name", dataType);
		DataSubType dataSubType = dataSubTypeDAO.findByField("name", dataSubtype);
		Map<String, Object> params = new HashMap<>();
		params.put("dataType.id", type.getId().toString());
		params.put("dataSubType.id", dataSubType.getId().toString());
		List<DataFile> list = dao.search(params, "uploadDate");

		if(latest && list != null && list.size() > 0) {
			DataFile latestFile = list.get(0);
			for(DataFile df: list) {
				if(df.getUploadDate().after(latestFile.getUploadDate()) && df.isValid()) {
					latestFile = df;
				}
			}
			ArrayList<DataFile> ret = new ArrayList<DataFile>();
			ret.add(latestFile);
			return ret;
		} else {
			return list;
		}
	}


}
