package org.alliancegenome.agr_submission.dao;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.DataType;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataTypeDAO extends BaseSQLDAO<DataType> {

	public DataTypeDAO() {
		super(DataType.class);
	}

}
