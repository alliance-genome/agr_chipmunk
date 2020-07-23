package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.DataSubType;
import org.alliancegenome.agr_submission.interfaces.server.DataSubTypeControllerInterface;
import org.alliancegenome.agr_submission.services.DataSubTypeService;

@RequestScoped
public class DataSubTypeController extends BaseController implements DataSubTypeControllerInterface {

	@Inject DataSubTypeService dataSubTypeService;
	
	@Override
	public DataSubType create(DataSubType entity) {
		return dataSubTypeService.create(entity);
	}

	@Override
	public DataSubType get(Long id) {
		return dataSubTypeService.get(id);
	}

	@Override
	public DataSubType update(DataSubType entity) {
		return dataSubTypeService.update(entity);
	}

	@Override
	public DataSubType delete(Long id) {
		return dataSubTypeService.delete(id);
	}
	
	public List<DataSubType> getDataSubTypes() {
		return dataSubTypeService.getDataSubTypes();
	}

}
