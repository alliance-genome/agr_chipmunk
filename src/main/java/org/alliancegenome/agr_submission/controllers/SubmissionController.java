package org.alliancegenome.agr_submission.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.exceptions.GenericException;
import org.alliancegenome.agr_submission.interfaces.server.SubmissionControllerInterface;
import org.alliancegenome.agr_submission.responces.APIResponce;
import org.alliancegenome.agr_submission.responces.SubmissionResponce;
import org.alliancegenome.agr_submission.services.SubmissionService;
import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.jcraft.jzlib.GZIPInputStream;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
@RequestScoped
public class SubmissionController extends BaseController implements SubmissionControllerInterface {

	@Inject
	private SubmissionService metaDataService;

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
			File outfile = new File(outFileName);

			try {
				InputStream is = inputPart.getBody(InputStream.class, null);

				log.info("Saving file to local filesystem: " + outfile.getAbsolutePath());
				FileUtils.copyInputStreamToFile(is, outfile);
				log.info("Save file to local filesystem complete");
				
				// Check for Valid GZIP header on the file.

				boolean passed = metaDataService.submitAndValidateDataFile(key, outfile, saveFile);

				if(passed) {
					res.getFileStatus().put(key, "success");
				} else {
					res.getFileStatus().put(key, "failed");
					success = false;
				}
			} catch (GenericException | IOException e) {
				log.error(e.getMessage());
				outfile.delete();
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

	@Override
	public Response getStableFile(String fileName) {
		
		DataType type = new DataType();
		// Lookup The latest reelase version
		// get the dataType and dataSubtype
		// get the latest URL 
		// craft download url: 
		
		Response.ResponseBuilder responseBuilder = null;
		if(fileName.endsWith(".gz")) {
			try {
				InputStream is = new URL("http://download.alliancegenome.org/3.1.1/BGI/RGD/1.0.1.2_BGI_RGD_0.json.gz").openStream();
				responseBuilder = Response.ok(is);
			} catch (IOException e) {
				responseBuilder = Response.noContent();
				e.printStackTrace();
			}
		} else if(fileName.endsWith(type.getFileExtension())) {
			try {
				InputStream is = new URL("http://download.alliancegenome.org/3.1.1/BGI/RGD/1.0.1.2_BGI_RGD_0.json").openStream();
				//GZIPInputStream gis = new GZIPInputStream(is);
				responseBuilder = Response.ok(is);
			} catch (IOException e) {
				responseBuilder = Response.noContent();
				e.printStackTrace();
			}
		} else {
			// Unknown format
		}
		
		responseBuilder.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		responseBuilder.type(MediaType.APPLICATION_OCTET_STREAM);
		return responseBuilder.build();
		
	}

}
