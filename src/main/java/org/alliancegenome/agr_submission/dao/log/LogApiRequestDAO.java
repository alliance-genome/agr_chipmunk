package org.alliancegenome.agr_submission.dao.log;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.log.LogApiRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogApiRequestDAO extends BaseSQLDAO<LogApiRequest> {

	public LogApiRequestDAO() {
		super(LogApiRequest.class);
	}
	
}