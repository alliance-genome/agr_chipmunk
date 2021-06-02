package org.alliancegenome.agr_submission.services;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.dao.*;
import org.alliancegenome.agr_submission.entities.*;
import org.alliancegenome.agr_submission.exceptions.*;
import org.alliancegenome.agr_submission.util.aws.S3Helper;
import org.alliancegenome.agr_submission.util.github.GitHelper;
import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.*;
import com.github.fge.jsonschema.main.*;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class SubmissionService {

	//@Inject private SnapShotDAO snapShotDAO;
	@Inject private ReleaseVersionService releaseService;
	@Inject private DataFileDAO dataFileDao;
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
			throw new ValidationException("Wrong Number of Args for File Data: " + key);
		}

		ReleaseVersion releaseVersion = null;

		if(releaseVersionLookup == null) {
			log.debug("Getting Next Release Version: ");
			releaseVersion = releaseService.getNextRelease();
		} else {
			log.debug("Looking up release Version: " + releaseVersionLookup);
			releaseVersion = releaseService.get(releaseVersionLookup);

		}

		if(releaseVersion == null) {
			throw new SchemaDataTypeException("Could not Find releaseVersion: " + releaseVersionLookup);
		}
		log.debug("Release Version: " + releaseVersion);

		DataType dataType = dataTypeDAO.findByField("name", dataTypeLookup);
		if(dataType == null) {
			throw new SchemaDataTypeException("Could not Find dataType: " + dataTypeLookup);
		}
		log.debug("Data Type: " + dataType);

		DataSubType dataSubType = dataSubTypeDAO.findByField("name", dataSubTypeLookup);

		if(dataSubType == null) {
			throw new SchemaDataTypeException("Could not Find dataSubType: " + dataSubTypeLookup);
		}
		log.debug("Data Sub Type: " + dataSubType);

		SchemaVersion schemaVersion = null;

		if(dataType.isValidationRequired()) {

			schemaVersion = releaseVersion.getDefaultSchemaVersion();

			if(schemaVersion == null) {
				throw new SchemaDataTypeException("Could not Find default schemaVersion for release: " + releaseVersionLookup);
			}
			log.debug("Schema Version: " + schemaVersion);

			validateData(releaseVersion, schemaVersion, dataType, inFile);
		}

		if(saveFile) {
			saveFile(releaseVersion, schemaVersion, dataType, dataSubType, inFile);
		}

		return true;
	}

	private boolean validateData(ReleaseVersion releaseVersion, SchemaVersion schemaVersionName, DataType dataType, File inFile) throws GenericException {

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

		if(!schemaFile.exists()) {
			throw new ValidationException("Schema File does not exist in schema Repo: agr_schemas_" + schemaVersionName.getSchema() + dataTypeFilePath);
		}

		try {

			JsonSchema schemaNode = JsonSchemaFactory.byDefault().getJsonSchema(schemaFile.toURI().toString());
			Reader reader = new InputStreamReader(new GZIPInputStream(new FileInputStream(inFile)));
			JsonNode jsonNode = JsonLoader.fromReader(reader);
			ProcessingReport report = schemaNode.validate(jsonNode);
			reader.close();

			if(!report.isSuccess()) {
				List<ProcessingMessage> list = new ArrayList<ProcessingMessage>();
				for(ProcessingMessage message: report) {
					list.add(message);
				}
				if(list.size() > 0) {
					throw new ValidationException(list.toString());
				}
			}
			log.info("Validation Complete: " + report.isSuccess());
			return report.isSuccess();
		} catch (IOException | ProcessingException e) {
			throw new ValidationException(e.getMessage());
		}

	}

	private void saveFile(ReleaseVersion releaseVersion, SchemaVersion schemaVersion, DataType dataType, DataSubType dataSubType, File inFile) throws GenericException {

		String dir = releaseVersion.getReleaseVersion() + "/" + dataType.getName() + "/" + dataSubType.getName() + "/";

		//String dir = schemaVersion.getSchema() + "/" + dataType.getName() + "/" + dataSubType.getName() + "/";

		int fileIndex = s3Helper.listFiles(dir);

		String filePath = null;

		String suffix = "";
		if(!dataType.getFileExtension().contains(".gz")) {
			suffix = ".gz";
		}
		
		if(schemaVersion != null) {
			filePath = dir + schemaVersion.getSchema() + "_" + dataType.getName() + "_" + dataSubType.getName() + "_" + fileIndex + "." + dataType.getFileExtension() + suffix;
		} else {
			filePath = dir + dataType.getName() + "_" + dataSubType.getName() + "_" + fileIndex + "." + dataType.getFileExtension() + suffix;
		}
		log.debug("File Save Path: " + filePath);

		try {
			InputStream is = new GZIPInputStream(new FileInputStream(inFile));
			String md5Sum = DigestUtils.md5Hex(is);
			is.close();

			log.info("MD5 Sum: " + md5Sum);

			// Check that data file doesn't already exist in the system if so update its release version
			DataFile df = dataFileDao.getByIdOrMD5Sum(md5Sum);
			if(df != null) {
				log.debug("DataFile found not uploading: " + df.getS3Path() + " MD5: " + df.getMd5Sum());
				boolean found = false;
				for(ReleaseVersion r: df.getReleaseVersions()) {
					log.trace("Release: " + r.getReleaseVersion() + " Release Lookup: " + releaseVersion.getReleaseVersion());
					if(r.getReleaseVersion().equals(releaseVersion.getReleaseVersion())) {
						log.debug("DataFile already under release: " + releaseVersion.getReleaseVersion());
						found = true;
						break;
					}
				}
				if(!found) {
					log.debug("Added DataFile to release version: " + releaseVersion.getReleaseVersion());
					df.getReleaseVersions().add(releaseVersion);
				}

			} else {
				log.debug("MD5 not found: creating new file: " + filePath);
				df = new DataFile();
				Set<ReleaseVersion> set = new HashSet<ReleaseVersion>();
				set.add(releaseVersion);
				df.setReleaseVersions(set);
				df.setSchemaVersion(schemaVersion);
				df.setDataType(dataType);
				df.setDataSubType(dataSubType);
				df.setS3Path(filePath);
				df.setMd5Sum(md5Sum);
				df.setUploadDate(new Date());
				log.info("Saving New File: " + filePath);
				// gzip compress the input stream on the fly and save it to s3
				s3Helper.saveFile(filePath, inFile);
			}
			dataFileDao.merge(df);

		} catch (Exception e) {
			throw new GenericException(e.getMessage());
		}

	}

}