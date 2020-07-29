package org.alliancegenome.agr_submission.controllers;

import lombok.extern.jbosslog.JBossLog;
import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.dao.DataSubTypeDAO;
import org.alliancegenome.agr_submission.dao.DataTypeDAO;
import org.alliancegenome.agr_submission.entities.DataFile;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.exceptions.GenericException;
import org.alliancegenome.agr_submission.exceptions.SchemaDataTypeException;
import org.alliancegenome.agr_submission.interfaces.server.SubmissionControllerInterface;
import org.alliancegenome.agr_submission.responces.APIResponce;
import org.alliancegenome.agr_submission.responces.SubmissionResponce;
import org.alliancegenome.agr_submission.services.DataFileService;
import org.alliancegenome.agr_submission.services.ReleaseVersionService;
import org.alliancegenome.agr_submission.services.SubmissionService;
import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@JBossLog
@RequestScoped
public class SubmissionController extends BaseController implements SubmissionControllerInterface {

	@Inject private SubmissionService metaDataService;
	@Inject private ReleaseVersionService releaseService;
	@Inject private DataFileService dataFileService;
	@Inject private DataTypeDAO dataTypeDAO;
	@Inject private DataSubTypeDAO dataSubTypeDAO;

	@Override
	public APIResponce submitData(MultipartFormDataInput input) {
		return processData(input, true);
	}

	@Override
	public APIResponce validateData(MultipartFormDataInput input) {
		return processData(input, false);
	}
	
	private APIResponce processData(MultipartFormDataInput input, boolean saveFile) {
		SubmissionResponce res = new SubmissionResponce();

		Map<String, List<InputPart>> form = input.getFormDataMap();
		boolean success = true;

		for(String key: form.keySet()) {
			InputPart inputPart = form.get(key).get(0);

			Date d = new Date();
			String outFileName = "tmp.data_" + d.getTime();
			File saveFilePath = new File(outFileName);

			try {
				InputStream is = inputPart.getBody(InputStream.class, null);

				log.info("Saving file to local filesystem: " + saveFilePath.getAbsolutePath());
				FileUtils.copyInputStreamToFile(is, saveFilePath);
				log.info("Save file to local filesystem complete");

				// if input stream is not gzipped, gzip-it
				try {
					GZIPInputStream gs = new GZIPInputStream(new FileInputStream(saveFilePath));
				} catch (IOException e) {
					log.info("Input stream not in the GZIP format, GZIP it");

					String gzFileName = outFileName + ".gz";
					if( !compressGzipFile(saveFilePath, gzFileName) ) {
						throw new GenericException("failed to gzip file");
					}
					log.info("gzipped to "+gzFileName);

					// delete original uncompressed file
					saveFilePath.delete();
					outFileName = gzFileName;
					saveFilePath = new File(gzFileName);
				}

				boolean passed = metaDataService.submitAndValidateDataFile(key, saveFilePath, saveFile);

				if(passed) {
					res.getFileStatus().put(key, "success");
				} else {
					res.getFileStatus().put(key, "failed");
					success = false;
				}
			} catch (GenericException | IOException e) {
				log.error(e.getMessage());
				saveFilePath.delete();
				res.getFileStatus().put(key, e.getMessage());
				//e.printStackTrace();
				success = false;
			}

		}
		if(success) {
			res.setStatus("success");
		} else {
			res.setStatus("failed");
		}
		return res;
	}

	private boolean compressGzipFile(File inFile, String gzipFile) {
		try ( FileInputStream fis = new FileInputStream(inFile);
			GZIPOutputStream gzipOS = new GZIPOutputStream(new FileOutputStream(gzipFile)) ) {

			byte[] buffer = new byte[4096];
			int len;
			while ((len = fis.read(buffer)) != -1) {
				gzipOS.write(buffer, 0, len);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Response getStableFile(String fileName) throws GenericException {

		String[] array = fileName.split("[_\\.]");

		DataType dataType = dataTypeDAO.findByField("name", array[0]);
		if(dataType == null) {
			throw new SchemaDataTypeException("Could not Find dataType: " + array[0]);
		}
		log.debug("Data Type: " + dataType);

		DataSubType dataSubType = dataSubTypeDAO.findByField("name", array[1]);

		if(dataSubType == null) {
			throw new SchemaDataTypeException("Could not Find dataSubType: " + array[1]);
		}
		log.debug("Data Sub Type: " + dataSubType);
		
		ReleaseVersion releaseVersion = releaseService.getCurrentRelease();
		log.debug("Current Release: " + releaseVersion);
		
		List<DataFile> dataFiles = dataFileService.getReleaseDataTypeSubTypeFiles(releaseVersion.getReleaseVersion(), dataType.getName(), dataSubType.getName(), true);
		
		Response.ResponseBuilder responseBuilder = null;
		if(dataFiles.size() == 1) {
			DataFile dataFile = dataFiles.get(0);
			
			String downloadHost = "";
			if(ConfigHelper.getAWSBucketName().equals("mod-datadumps")) {
				downloadHost = "http://download.alliancegenome.org/";
			} else if(ConfigHelper.getAWSBucketName().contentEquals("mod-datadumps-dev")) {
				downloadHost = "http://downloaddev.alliancegenome.org/";
			} else {
				downloadHost = "http://localhost:8080/";
			}
			
			if(fileName.endsWith(".gz")) {
				try {
					InputStream is = new URL(downloadHost + dataFile.getS3Path()).openStream();
					responseBuilder = Response.ok(is);
				} catch (IOException e) {
					responseBuilder = Response.noContent();
					e.printStackTrace();
				}
			} else if(fileName.endsWith(dataType.getFileExtension())) {
				try {
					InputStream is = new URL(downloadHost + dataFile.getS3Path()).openStream();
					//GZIPInputStream gis = new GZIPInputStream(is);
					responseBuilder = Response.ok(is);
				} catch (IOException e) {
					responseBuilder = Response.noContent();
					e.printStackTrace();
				}
			} else {
				// Unknown format
			}

		} else {
			responseBuilder = Response.noContent();
		}
		
		responseBuilder.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		responseBuilder.type(MediaType.APPLICATION_OCTET_STREAM);
		return responseBuilder.build();		

	}

}
