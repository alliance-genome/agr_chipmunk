package org.alliancegenome.agr_submission.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.dao.DataSubTypeDAO;
import org.alliancegenome.agr_submission.dao.DataTypeDAO;
import org.alliancegenome.agr_submission.dao.SchemaVersionDAO;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.exceptions.GenericException;
import org.alliancegenome.agr_submission.exceptions.SchemaDataTypeException;
import org.alliancegenome.agr_submission.exceptions.ValidataionException;
import org.alliancegenome.agr_submission.util.aws.S3Helper;
import org.alliancegenome.agr_submission.util.github.GitHelper;
import org.apache.commons.codec.digest.DigestUtils;

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

	//@Inject private SnapShotDAO snapShotDAO;
	@Inject private ReleaseVersionService releaseService;
	@Inject private DataFileService dataFileService;
	@Inject private SchemaVersionDAO schemaVersionDAO;
	@Inject private DataTypeDAO dataTypeDAO;
	@Inject private DataSubTypeDAO dataSubTypeDAO;

	private static GitHelper gitHelper = new GitHelper();
	private static S3Helper s3Helper = new S3Helper();

	@Transactional
	public boolean submitAndValidateDataFile(String key, File inFile, boolean saveFile) throws GenericException {

		// Split the keys by underscore
		String[] keys = key.split("_");

		String releaseVersionLookup;
		String dataTypeLookup;
		String dataSubTypeLookup;

		if(keys.length == 3) {
			log.debug("Key has 3 items: parse: (Release-DataType-DataSubType): " + key);
			releaseVersionLookup = keys[0];
			dataTypeLookup = keys[1];
			dataSubTypeLookup = keys[2];
		} else if(keys.length == 2) { // DataType-DataSubType puts the file under the next release
			log.debug("Key has 2 items: parse: (DataType-DataSubType): " + key);
			releaseVersionLookup = null;
			dataTypeLookup = keys[0];
			dataSubTypeLookup = keys[1];
		} else {
			throw new ValidataionException("Wrong Number of Args for File Data: " + key);
		}
		
		ReleaseVersion releaseVersion = null;
		
		if(releaseVersionLookup == null) {
			releaseVersion = releaseService.getNextRelease();
		} else {
			releaseVersion = releaseService.get(releaseVersionLookup);
		}
		
		if(releaseVersion == null) {
			throw new SchemaDataTypeException("Could not Find releaseVersion: " + releaseVersionLookup);
		}
		
		DataType dataType = dataTypeDAO.findByField("name", dataTypeLookup);
		if(dataType == null) {
			throw new SchemaDataTypeException("Could not Find dataType: " + dataTypeLookup);
		}
		
		DataSubType dataSubType = dataSubTypeDAO.findByField("name", dataSubTypeLookup);

		if(dataSubType == null) {
			throw new SchemaDataTypeException("Could not Find dataSubType: " + dataSubTypeLookup);
		}
		
		SchemaVersion schemaVersion = null;
		
		if(dataType.isValidationRequired()) {
			
			schemaVersion = releaseVersion.getDefaultSchemaVersion();

			if(schemaVersion == null) {
				throw new SchemaDataTypeException("Could not Find default schemaVersion for release: " + releaseVersionLookup);
			}
			
			validateData(schemaVersion, dataType, inFile);
		}
		
		if(saveFile) {
			saveFile(releaseVersion, schemaVersion, dataType, dataSubType, inFile);
		}

		return true;
	}

	private boolean validateData(SchemaVersion schemaVersionName, DataType dataType, File inFile) throws GenericException {

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


	private void saveFile(ReleaseVersion releaseVersion, SchemaVersion schemaVersion, DataType dataType, DataSubType dataSubType, File inFile) throws GenericException {

		String dir = releaseVersion.getReleaseVersion() + "/" + dataType.getName() + "/" + dataSubType.getName() + "/";
		
		//String dir = schemaVersion.getSchema() + "/" + dataType.getName() + "/" + dataSubType.getName() + "/";

		int fileIndex = s3Helper.listFiles(dir);

		String filePath = null;
		
		if(schemaVersion != null) {
			filePath = dir + schemaVersion.getSchema() + "_" + dataType.getName() + "_" + dataSubType.getName() + "_" + fileIndex + "." + dataType.getFileExtension();
		} else {
			filePath = dir + dataType.getName() + "_" + dataSubType.getName() + "_" + fileIndex + "." + dataType.getFileExtension();
		}

		try {
			FileInputStream fis = new FileInputStream(inFile);
			String md5Sum = DigestUtils.md5Hex(fis);
			fis.close();
			log.info("MD5 Sum: " + md5Sum);

			// Check that data file doesn't already exist in the system if so update its release version
			DataFile df = dataFileService.get(md5Sum);
			if(df != null) {
				boolean found = false;
				for(ReleaseVersion r: df.getReleaseVersions()) {
					if(r.getReleaseVersion().contentEquals(releaseVersion.getReleaseVersion())) {
						found = true;
						break;
					}
				}
				if(!found) {
					df.getReleaseVersions().add(releaseVersion);
				}
				
			} else {
				df = new DataFile();
				df.getReleaseVersions().add(releaseVersion);
				df.setSchemaVersion(schemaVersion);
				df.setDataType(dataType);
				df.setDataSubType(dataSubType);
				df.setS3Path(filePath);
				df.setMd5Sum(md5Sum);
				df.setUploadDate(new Date());
				log.info("Saving New File: " + filePath);
				s3Helper.saveFile(filePath, inFile);
			}
			dataFileService.update(df);
			
		} catch (Exception e) {
			throw new GenericException(e.getMessage());
		}
		
	}

}