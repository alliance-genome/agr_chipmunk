package org.alliancegenome.agr_submission.dao;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.SchemaFile;

@ApplicationScoped
public class SchemaFileDAO extends BaseSQLDAO<SchemaFile> {

	public SchemaFileDAO() {
		super(SchemaFile.class);
	}

}
