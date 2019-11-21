package org.alliancegenome.agr_submission.dao.log;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.log.LogApiRequest;

@ApplicationScoped
public class LogApiRequestDAO extends BaseSQLDAO<LogApiRequest> {

	public LogApiRequestDAO() {
		super(LogApiRequest.class);
	}
	
}