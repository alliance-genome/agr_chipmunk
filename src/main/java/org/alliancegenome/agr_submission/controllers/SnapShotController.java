package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.SnapShot;
import org.alliancegenome.agr_submission.interfaces.server.SnapShotControllerInterface;
import org.alliancegenome.agr_submission.responces.APIResponce;
import org.alliancegenome.agr_submission.responces.SnapShotResponce;
import org.alliancegenome.agr_submission.services.SnapShotService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SnapShotController extends BaseController implements SnapShotControllerInterface {

	@Inject SnapShotService snapShotService;
	
	@Override
	public SnapShot create(SnapShot entity) {
		return snapShotService.create(entity);
	}

	@Override
	public SnapShot get(Long id) {
		return snapShotService.get(id);
	}

	@Override
	public SnapShot update(SnapShot entity) {
		return snapShotService.update(entity);
	}

	@Override
	public SnapShot delete(Long id) {
		return snapShotService.delete(id);
	}
	
	public List<SnapShot> getSnapShots() {
		return snapShotService.getSnapShots();
	}

	@Override
	public SnapShotResponce takeSnapShot(String releaseVersion) {
		SnapShot ssd = snapShotService.takeSnapShot(releaseVersion);
		SnapShotResponce res = new SnapShotResponce();
		res.setSnapShot(ssd);
		res.setStatus("success");
		return res;
	}

	@Override
	public APIResponce getSnapShot(String releaseVersion) {
		SnapShot ssd = snapShotService.getLatestShapShot(releaseVersion);
		SnapShotResponce res = new SnapShotResponce();
		res.setSnapShot(ssd);
		res.setStatus("success");
		return res;
	}

}
