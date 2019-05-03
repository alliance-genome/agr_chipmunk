package org.alliancegenome.agr_submission.services;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.dao.DataFileDAO;
import org.alliancegenome.agr_submission.dao.DataSubTypeDAO;
import org.alliancegenome.agr_submission.dao.DataTypeDAO;
import org.alliancegenome.agr_submission.dao.ReleaseVersionDAO;
import org.alliancegenome.agr_submission.dao.SchemaVersionDAO;
import org.alliancegenome.agr_submission.dao.SnapShotDAO;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.exceptions.GenericException;
import org.alliancegenome.agr_submission.exceptions.SchemaDataTypeException;
import org.alliancegenome.agr_submission.exceptions.ValidataionException;
import org.alliancegenome.agr_submission.util.aws.S3Helper;
import org.alliancegenome.agr_submission.util.github.GitHelper;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
@RequestScoped
public class SubmissionService {

	@Inject private SnapShotDAO snapShotDAO;
	@Inject private ReleaseVersionDAO releaseDAO;
	@Inject private DataFileDAO dataFileDAO;
	@Inject private SchemaVersionDAO schemaVersionDAO;
	@Inject private DataTypeDAO dataTypeDAO;
	@Inject private DataSubTypeDAO dataSubTypeDAO;
	
	private static GitHelper gitHelper = new GitHelper();
	private static S3Helper s3Helper = new S3Helper();

	@Transactional
	public boolean submitAndValidateDataFile(String key, File inFile, boolean saveFile) throws GenericException {

		// Split the keys by underscore
		String[] keys = key.split("_");

		String schemaLookup;
		String dataTypeLookup;
		String dataSubTypeLookup;
		
		if(keys.length == 3) { // Schema-DataType-TaxonId
			log.debug("Key has 3 items: parse: (Schema-DataType-TaxonId): " + key);
			schemaLookup = keys[0];
			dataTypeLookup = keys[1];
			dataSubTypeLookup = keys[2];
		} else if(keys.length == 2) { // DataType-TaxonId // Input a taxonId datatype file and validate against latest version of schema
			log.debug("Key has 2 items: parse: (DataType-TaxonId): " + key);
			schemaLookup = null;
			dataTypeLookup = keys[0];
			dataSubTypeLookup = keys[1];
		} else {
			throw new ValidataionException("Wrong Number of Args for File Data: " + key);
		}
		
		SchemaVersion schemaVersion = getSchemaVersion(schemaLookup);
		DataType dataType = dataTypeDAO.findByField("name", dataTypeLookup);
		DataSubType dataSubType = dataSubTypeDAO.findByField("name", dataSubTypeLookup);
		
		if(dataType.isValidationRequired()) {
			validateData(schemaVersion, dataType, inFile);
		}
	
		if(saveFile) {
			saveFile(schemaVersion, dataType, dataSubType, inFile);
		}
		return true;
	}

	private boolean validateData(SchemaVersion schemaVersionName, DataType dataType, File inFile) throws GenericException {

//		// TODO fix this method to actullly validate data
		log.info("Need to validate file: " + schemaVersionName.getSchema() + " " + dataType.getName());
		String dataTypeFilePath = dataType.getSchemaFilesMap().get(schemaVersionName.getSchema());

		if(dataTypeFilePath == null) {
			log.info("No Data type file found for: " + schemaVersionName.getSchema() + " looking backwards for older schema versions");

			String previousVersion = null;
			for(previousVersion = schemaVersionDAO.getPreviousVersion(schemaVersionName.getSchema()); previousVersion != null;	previousVersion = schemaVersionDAO.getPreviousVersion(previousVersion) ) {
				if(dataType.getSchemaFilesMap().get(previousVersion) != null) {
					dataTypeFilePath = dataType.getSchemaFilesMap().get(previousVersion);
					log.info("Found File name for: " + previousVersion + " -> " + dataTypeFilePath);
					break;
				}
			}
			if(previousVersion == null) {
				throw new SchemaDataTypeException("No Schema file for Data Type found: schema: " + schemaVersionName + " dataType: " + dataType.getName());
			} else {
				log.info("Previous Version Found: " + previousVersion);
			}
		}
		File schemaFile = gitHelper.getFile(schemaVersionName.getSchema(), dataTypeFilePath);

		try {

			JsonSchema schemaNode = JsonSchemaFactory.byDefault().getJsonSchema(schemaFile.toURI().toString());
			JsonNode jsonNode = JsonLoader.fromFile(inFile);

			ProcessingReport report = schemaNode.validate(jsonNode);

			if(!report.isSuccess()) {
				for(ProcessingMessage message: report) {
					throw new ValidataionException(message.getMessage());
				}
			}
			log.info("Validation Complete: " + report.isSuccess());
			return report.isSuccess();
		} catch (IOException | ProcessingException e) {
			throw new ValidataionException(e.getMessage());
		}

	}

	private SchemaVersion getSchemaVersion(String schemaString) throws ValidataionException {
		if(schemaString == null) {
			SchemaVersion schemaVersion = schemaVersionDAO.getCurrentSchemaVersion();
			return schemaVersion;
		} else {
			SchemaVersion schemaVersion = schemaVersionDAO.getSchemaVersion(schemaString);
			if(schemaVersion == null) {
				throw new ValidataionException("Schema Version not found: " + schemaString);
			}
			return schemaVersion;
		}
	}

	private void saveFile(SchemaVersion schemaVersion, DataType dataType, DataSubType dataSubType, File inFile) throws GenericException {
		if(dataSubType == null) {
			int fileIndex = s3Helper.listFiles(schemaVersion + "/" + dataType.getName() + "/");
			String filePath = schemaVersion.getSchema() + "/" + dataType.getName() + "/" + schemaVersion.getSchema() + "_" + dataType.getName() + "_" + fileIndex + "." + dataType.getFileExtension();
			s3Helper.saveFile(filePath, inFile);
			createDataFile(schemaVersion, dataType, null, filePath);
		} else {
			int fileIndex = s3Helper.listFiles(schemaVersion.getSchema() + "/" + dataType.getName() + "/" + dataSubType.getName() + "/");

			String filePath =
					schemaVersion.getSchema() + "/" + dataType.getName() + "/" + dataSubType.getName() + "/" +
							schemaVersion.getSchema() + "_" + dataType.getName() + "_" + dataSubType.getName() + "_" + fileIndex + "." + dataType.getFileExtension();

			s3Helper.saveFile(filePath, inFile);
			createDataFile(schemaVersion, dataType, dataSubType, filePath);
		}
	}
	
	private void createDataFile(SchemaVersion schemaVersion, DataType dataType, DataSubType dataSubType, String filePath) {
		DataFile df = new DataFile();
		df.setDataType(dataType);
		df.setS3Path(filePath);
		df.setSchemaVersion(schemaVersion);
		df.setDataSubType(dataSubType);
		df.setUploadDate(new Date());
		dataFileDAO.persist(df);
	}


}