package org.alliancegenome.agr_submission.dao;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.DataSubType;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataSubTypeDAO extends BaseSQLDAO<DataSubType> {

	public DataSubTypeDAO() {
		super(DataSubType.class);
	}

}
