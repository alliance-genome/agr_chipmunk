package org.alliancegenome.agr_submission.dao.log;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.log.LogUserAgent;

@ApplicationScoped
public class LogUserAgentDAO extends BaseSQLDAO<LogUserAgent> {

	public LogUserAgentDAO() {
		super(LogUserAgent.class);
	}
	
	public LogUserAgent save(String userAgent) {
		LogUserAgent agent = findByField("userAgent", userAgent);
		if(agent == null) {
			LogUserAgent newAgent = new LogUserAgent();
			newAgent.setUserAgent(userAgent);
			return persist(newAgent);
		} else {
			return agent;
		}
	}
}
