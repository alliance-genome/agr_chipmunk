package org.alliancegenome.agr_submission.dao;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.DataSubType;

@ApplicationScoped
public class DataSubTypeDAO extends BaseSQLDAO<DataSubType> {

	public DataSubTypeDAO() {
		super(DataSubType.class);
	}

}
