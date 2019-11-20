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
import org.alliancegenome.agr_submission.dao.ReleaseVersionDAO;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class DataFileService extends BaseService<DataFile> {

	@Inject private DataFileDAO dao;
	@Inject private DataTypeDAO dataTypeDAO;
	@Inject private DataSubTypeDAO dataSubTypeDAO;
	@Inject private ReleaseVersionDAO releaseDAO;

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
	public DataFile create(String releaseVersion, String dataType, String dataSubtype, DataFile entity) {
		log.info("DataFileService: create: ");
		DataType type = dataTypeDAO.findByField("name", dataType);
		DataSubType dataSubType = dataSubTypeDAO.findByField("name", dataSubtype);
		ReleaseVersion rv = releaseDAO.findByField("releaseVersion", releaseVersion);
		SchemaVersion sv = rv.getDefaultSchemaVersion();

		if(type == null || dataSubType == null || sv == null) {
			log.error("Was not able to find everything needed to create data file: " + type + " " + dataSubType + " " + sv);
			return null;
		}

		entity.getReleaseVersions().add(rv);
		entity.setSchemaVersion(sv);
		entity.setDataType(type);
		entity.setDataSubType(dataSubType);
		return dao.persist(entity);
	}
	
	@Override
	@Transactional
	public DataFile get(Long id) {
		return dao.find(id);
	}
	
	@Transactional
	public DataFile get(String id) {
		return dao.getByIdOrMD5Sum(id);
	}

	@Override
	@Transactional
	public DataFile update(DataFile entity) {
		log.info("DataFileService: update: ");
		DataFile dbEntity = get(entity.getId());
		dbEntity.setMd5Sum(entity.getMd5Sum());
		dbEntity.setS3Path(entity.getS3Path());
		dbEntity.setUrlPath(entity.getUrlPath());
		dbEntity.setValid(entity.getValid());
		dbEntity.setUploadDate(entity.getUploadDate());
		return dao.merge(dbEntity);
	}

	@Override
	@Transactional
	public DataFile delete(Long id) {
		log.info("DataFileService: delete: " + id);
		return dao.remove(id);
	}

	@Transactional
	public List<DataFile> assignDataFilesFromRelease1ToRelease2(String releaseVersion1, String releaseVersion2) {
		
		List<DataFile> list1 = getDataFilesByRelease(releaseVersion1, true); // Only looking to add the latest files to the new release
		ReleaseVersion rv2 = releaseDAO.findByField("releaseVersion", releaseVersion2);
		
		if(list1 == null || list1.isEmpty() || rv2 == null) {
			log.error("Was not able to find everything needed to assign Release Versions: " + releaseVersion1 + " " + releaseVersion2);
			return null;
		}
		
		Map<String, DataFile> map = new HashMap<String, DataFile>();
		log.debug("List: " + list1.size());
		
		for(DataFile df: rv2.getDataFiles()) {
			map.put(df.getMd5Sum(), df);
		}
		
		log.debug("Before: " + rv2.getDataFiles().size());
		for(DataFile df: list1) {
			if(!map.containsKey(df.getMd5Sum())) {
				map.put(df.getMd5Sum(), df);
				df.getReleaseVersions().add(rv2);
				rv2.getDataFiles().add(df);
			}
		}
		log.debug("After: " + rv2.getDataFiles().size());
		
		releaseDAO.merge(rv2);
		
		return new ArrayList<DataFile>(rv2.getDataFiles());
	}
	
	public List<DataFile> getDataFiles() {
		return dao.findAll();
	}

	@Transactional
	public List<DataFile> getDataTypeFiles(String dataType, Boolean latest) {
		DataType type = dataTypeDAO.findByField("name", dataType);
		if(type != null) {
			Map<String, Object> params = new HashMap<>();
			params.put("dataType.id", type.getId().toString());
			List<DataFile> list = dao.search(params, "uploadDate");
			
			if(latest) {
				MultiKeyMap<String, DataFile> map = new MultiKeyMap<>();
				for(DataFile df: list) {
					MultiKey<String> key = new MultiKey<String>(df.getDataType().getName(), df.getDataSubType().getName());
					
					DataFile dataFile = map.get(key);
					
					if(dataFile == null || (df.getUploadDate().after(dataFile.getUploadDate()) && df.isValid())) {
						dataFile = df;
					}
					map.put(key, dataFile);
				}
				return new ArrayList<DataFile>(map.values());
			} else {
				return list;
			}

		} else {
			return null;
		}
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
	
	@Transactional
	public List<DataFile> getDataFilesByRelease(String releaseVersion, Boolean latest) {
		ReleaseVersion releaseVersionLookup = releaseDAO.findByField("releaseVersion", releaseVersion);
	
		if(latest) {
			MultiKeyMap<String, DataFile> map = new MultiKeyMap<>();
			for(DataFile df: releaseVersionLookup.getDataFiles()) {
				MultiKey<String> key = new MultiKey<String>(df.getDataType().getName(), df.getDataSubType().getName());
				
				DataFile dataFile = map.get(key);
				
				if(dataFile == null || (df.getUploadDate().after(dataFile.getUploadDate()) && df.isValid())) {
					dataFile = df;
				}
				map.put(key, dataFile);
			}
	
			return new ArrayList<DataFile>(map.values());
		} else {
			return new ArrayList<DataFile>(releaseVersionLookup.getDataFiles());
		}
	}

	public List<DataFile> getReleaseDataTypeSubTypeFiles(String releaseVersion, String dataType, String dataSubtype, Boolean latest) {
		ReleaseVersion releaseVersionLookup = releaseDAO.findByField("releaseVersion", releaseVersion);
		List<DataFile> files = getDataTypeSubTypeFiles(dataType, dataSubtype, latest);
		ArrayList<DataFile> ret = new ArrayList<DataFile>();
		
		for(DataFile df: files) {
			for(ReleaseVersion rv: df.getReleaseVersions()) {
				if(rv.getReleaseVersion().equals(releaseVersionLookup.getReleaseVersion())) {
					ret.add(df);
					break;
				}
			}
		}
		
		return ret;
	}

	@Transactional
	public DataFile validateToggle(String id) {
		DataFile dbEntity = get(id);
		dbEntity.setValid(!dbEntity.isValid());
		return dao.merge(dbEntity);
	}

}
