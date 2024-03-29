package org.alliancegenome.agr_submission.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.alliancegenome.agr_submission.BaseController;
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
import org.alliancegenome.agr_submission.util.GZIPCompressingInputStream;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.google.common.base.Joiner;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class SubmissionController extends BaseController implements SubmissionControllerInterface {

	@ConfigProperty(name = "aws.bucket.host") String downloadHost;
	
	@Inject SubmissionService metaDataService;
	@Inject ReleaseVersionService releaseService;
	@Inject DataFileService dataFileService;
	@Inject DataTypeDAO dataTypeDAO;
	@Inject DataSubTypeDAO dataSubTypeDAO;

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

				Log.info("Saving file to local filesystem: " + saveFilePath.getAbsolutePath());
				FileUtils.copyInputStreamToFile(is, saveFilePath);
				Log.info("Save file to local filesystem complete");

				// if input stream is not gzipped, gzip-it
				try {
					GZIPInputStream gs = new GZIPInputStream(new FileInputStream(saveFilePath));
					gs.close();
				} catch (IOException e) {
					Log.info("Input stream not in the GZIP format, GZIP it");

					String gzFileName = outFileName + ".gz";
					if( !compressGzipFile(saveFilePath, gzFileName) ) {
						throw new GenericException("failed to gzip file");
					}
					Log.info("gzipped to "+gzFileName);

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
				Log.error(e.getMessage());
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

		List<String> parts = new ArrayList<String>(Arrays.asList(fileName.split("[_\\.]"))); // Takes care of any sub types with dots

		String dataTypeString = parts.remove(0);

		DataType dataType = dataTypeDAO.findByField("name", dataTypeString);
		if(dataType == null) {
			throw new SchemaDataTypeException("Could not Find dataType: " + dataTypeString);
		}
		Log.debug("Data Type: " + dataType);
		
		boolean gzFileRequest = false;
		
		if(parts.get(parts.size() - 1).equals("gz")) {
			parts.remove(parts.size() - 1);
			gzFileRequest = true;
		}
		
		if(parts.get(parts.size() - 1).equals(dataType.getFileExtension())) {
			parts.remove(parts.size() - 1);
		}
		
		String dataSubTypeString = Joiner.on(".").join(parts);

		DataSubType dataSubType = dataSubTypeDAO.findByField("name", dataSubTypeString);

		if(dataSubType == null) {
			throw new SchemaDataTypeException("Could not Find dataSubType: " + dataSubTypeString);
		}
		Log.debug("Data Sub Type: " + dataSubType);
		
		ReleaseVersion releaseVersion = releaseService.getCurrentRelease();
		Log.debug("Current Release: " + releaseVersion);
		
		List<DataFile> dataFiles = dataFileService.getReleaseDataTypeSubTypeFiles(releaseVersion.getReleaseVersion(), dataType.getName(), dataSubType.getName(), true);
		
		Response.ResponseBuilder responseBuilder = null;
		if(dataFiles.size() == 1) {
			DataFile dataFile = dataFiles.get(0);
			
			try {
				if(gzFileRequest) {
					// Person asked for compressed version
					if(dataFile.getS3Path().endsWith(".gz")) {
						// Already compressed send straight through
						Log.info("Download: " + downloadHost + "/" + dataFile.getS3Path());
						InputStream is = new URL(downloadHost + "/" + dataFile.getS3Path()).openStream();
						responseBuilder = Response.ok(is);
					} else {
						// Not compressed, compress file and send
						GZIPCompressingInputStream gis = new GZIPCompressingInputStream(new URL(downloadHost + "/" + dataFile.getS3Path()).openStream());
						responseBuilder = Response.ok(gis);
					}
				} else {
					// Person asked for uncompressed version
					if(dataFile.getS3Path().endsWith(".gz")) {
						// Already compressed, uncompress file and send
						GZIPInputStream gis = new GZIPInputStream(new URL(downloadHost + "/" + dataFile.getS3Path()).openStream());
						responseBuilder = Response.ok(gis);
					} else {
						// Already uncompressed send straight through
						InputStream is = new URL(downloadHost + "/" + dataFile.getS3Path()).openStream();
						responseBuilder = Response.ok(is);
					}
				}
			} catch (IOException e) {
				responseBuilder = Response.noContent();
				e.printStackTrace();
			}

		} else {
			responseBuilder = Response.noContent();
		}
		
		responseBuilder.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		responseBuilder.type(MediaType.APPLICATION_OCTET_STREAM);
		return responseBuilder.build();		

	}


}
