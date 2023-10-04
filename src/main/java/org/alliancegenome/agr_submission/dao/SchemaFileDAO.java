package org.alliancegenome.agr_submission.dao;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.SchemaFile;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SchemaFileDAO extends BaseSQLDAO<SchemaFile> {

	public SchemaFileDAO() {
		super(SchemaFile.class);
	}

}
