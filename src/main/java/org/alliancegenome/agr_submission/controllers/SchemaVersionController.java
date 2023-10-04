package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.interfaces.server.SchemaVersionControllerInterface;
import org.alliancegenome.agr_submission.services.SchemaVersionService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SchemaVersionController extends BaseController implements SchemaVersionControllerInterface {

	@Inject SchemaVersionService schemaVersionService;
	
	@Override
	public SchemaVersion create(SchemaVersion entity) {
		return schemaVersionService.create(entity);
	}

	@Override
	public SchemaVersion get(Long id) {
		return schemaVersionService.get(id);
	}

	@Override
	public SchemaVersion update(SchemaVersion entity) {
		return schemaVersionService.update(entity);
	}

	@Override
	public SchemaVersion delete(Long id) {
		return schemaVersionService.delete(id);
	}

	@Override
	public SchemaVersion getCurrentSchemaVersion() {
		return schemaVersionService.getCurrentSchemaVersion();
	}
	
	@Override
	public List<SchemaVersion> getSchemaVersions() {
		return schemaVersionService.getSchemaVersions();
	}

}
