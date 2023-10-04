package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.SchemaFile;
import org.alliancegenome.agr_submission.interfaces.server.SchemaFileControllerInterface;
import org.alliancegenome.agr_submission.services.SchemaFileService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
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
