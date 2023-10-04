package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.DataType;
import org.alliancegenome.agr_submission.entities.SchemaFile;
import org.alliancegenome.agr_submission.forms.AddDataSubTypeForm;
import org.alliancegenome.agr_submission.forms.CreateSchemaFileForm;
import org.alliancegenome.agr_submission.interfaces.server.DataTypeControllerInterface;
import org.alliancegenome.agr_submission.services.DataTypeService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class DataTypeController extends BaseController implements DataTypeControllerInterface {

	@Inject DataTypeService dataTypeService;
	
	@Override
	public DataType create(DataType entity) {
		return dataTypeService.create(entity);
	}

	@Override
	public DataType get(Long id) {
		return dataTypeService.get(id);
	}

	@Override
	public DataType update(DataType entity) {
		return dataTypeService.update(entity);
	}

	@Override
	public DataType delete(Long id) {
		return dataTypeService.delete(id);
	}
	
	public List<DataType> getDataTypes() {
		return dataTypeService.getDataTypes();
	}

	@Override
	public DataType addSchemaFile(String dataType, CreateSchemaFileForm form) {
		return dataTypeService.addSchemaFile(dataType, form);
	}

	@Override
	public DataType addDataSubType(String dataType, AddDataSubTypeForm form) {
		return dataTypeService.addDataSubType(dataType, form.getDataSubType());
	}

	@Override
	public SchemaFile deleteSchemaFile(String dataType, Long id) {
		return dataTypeService.deleteSchemaFile(dataType, id);
	}

}
