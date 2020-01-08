package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.entities.SnapShot;
import org.alliancegenome.agr_submission.interfaces.server.ReleaseVersionControllerInterface;
import org.alliancegenome.agr_submission.services.ReleaseVersionService;

@RequestScoped
public class ReleaseVersionController extends BaseController implements ReleaseVersionControllerInterface {

	@Inject ReleaseVersionService releaseVersionService;
	
	@Override
	public ReleaseVersion create(ReleaseVersion entity) {
		return releaseVersionService.create(entity);
	}

	@Override
	public ReleaseVersion get(String id) {
		return releaseVersionService.get(id);
	}
	
	@Override
	public List<SnapShot> getSnapshots(String id) {
		return releaseVersionService.getSnapshots(id);
	}

	@Override
	public ReleaseVersion update(ReleaseVersion entity) {
		return releaseVersionService.update(entity);
	}

	@Override
	public ReleaseVersion delete(Long id) {
		return releaseVersionService.delete(id);
	}
	
	public List<ReleaseVersion> getReleaseVersions() {
		return releaseVersionService.getReleaseVersions();
	}

	@Override
	public ReleaseVersion setSchema(String release, String schema) {
		return releaseVersionService.setSchema(release, schema);
	}
	
}
