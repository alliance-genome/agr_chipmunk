package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.SchemaFile;
import org.alliancegenome.agr_submission.interfaces.server.SchemaFileControllerInterface;
import org.alliancegenome.agr_submission.services.SchemaFileService;

@RequestScoped
@WebService
public class SchemaFileController extends BaseController implements SchemaFileControllerInterface {

	@Inject SchemaFileService schemaFileService;
	
	@Override
	public SchemaFile create(SchemaFile entity) {
		return schemaFileService.create(entity);
	}

	@Override
	public SchemaFile get(Long id) {
		return schemaFileService.get(id);
	}

	@Override
	public SchemaFile update(SchemaFile entity) {
		return schemaFileService.update(entity);
	}

	@Override
	public SchemaFile delete(Long id) {
		return schemaFileService.delete(id);
	}
	
	public List<SchemaFile> getSchemaFiles() {
		return schemaFileService.getSchemaFiles();
	}


}
