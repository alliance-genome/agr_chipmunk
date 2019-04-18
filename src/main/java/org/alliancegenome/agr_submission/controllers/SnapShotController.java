package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.SnapShot;
import org.alliancegenome.agr_submission.interfaces.server.SnapShotControllerInterface;
import org.alliancegenome.agr_submission.services.SnapShotService;

@RequestScoped
@WebService
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

}
