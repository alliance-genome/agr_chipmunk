package org.alliancegenome.agr_submission.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.SnapShot;
import org.alliancegenome.agr_submission.exceptions.GenericException;
import org.alliancegenome.agr_submission.interfaces.server.SubmissionControllerInterface;
import org.alliancegenome.agr_submission.responces.APIResponce;
import org.alliancegenome.agr_submission.responces.GetReleasesResponce;
import org.alliancegenome.agr_submission.responces.SnapShotResponce;
import org.alliancegenome.agr_submission.responces.SubmissionResponce;
import org.alliancegenome.agr_submission.services.SubmissionService;
import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
@RequestScoped
@WebService
public class SubmissionController extends BaseController implements SubmissionControllerInterface {

	@Inject
	private SubmissionService metaDataService;

	@Override
	public SubmissionResponce submitData(String api_access_token, MultipartFormDataInput input) {
		SubmissionResponce res = new SubmissionResponce();

		Map<String, List<InputPart>> form = input.getFormDataMap();
		boolean success = true;

		if(authenticate(api_access_token)) {
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

					metaDataService.submitAndValidateDataFile(key, outfile, true);
					res.getFileStatus().put(key, "success");
				} catch (GenericException | IOException e) {
					log.error(e.getMessage());
					outfile.delete();
					res.getFileStatus().put(key, e.getMessage());
					//e.printStackTrace();
					success = false;
				}
			} 
		} else {
			res.getFileStatus().put("Auth Failure", "Authentication Failure: Please check your api_access_token");
			success = false;
		}

		if(success) {
			res.setStatus("success");
		} else {
			res.setStatus("failed");
		}
		return res;

	}

	@Override
	public APIResponce validateData(MultipartFormDataInput input) {
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

				boolean passed = metaDataService.submitAndValidateDataFile(key, outfile, false);

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
	public APIResponce takeSnapShot(String api_access_token, String releaseVersion) {
		SnapShotResponce res = new SnapShotResponce();
		res.setStatus("success");
		if(authenticate(api_access_token)) {
			SnapShot ssd = metaDataService.takeSnapShot(releaseVersion);
			res.setSnapShot(ssd);
		} else {
			res.setStatus("failed");
			res.setMessage("Authentication Failure: Please check your api_access_token");
		}
		return res;
	}

	@Override
	public APIResponce getSnapShot(String releaseVersion) {
		SnapShotResponce res = new SnapShotResponce();
		res.setStatus("success");
		SnapShot ssd = metaDataService.getShapShot(releaseVersion);
		res.setSnapShot(ssd);
		return res;
	}

	@Override
	public APIResponce getReleases() {
		GetReleasesResponce res = new GetReleasesResponce();
		res.setStatus("success");
		res.setReleases(metaDataService.getReleases());
		return res;
	}

}
