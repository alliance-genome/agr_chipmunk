package org.alliancegenome.agr_submission.dao.log;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.log.LogRequestUri;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogRequestUriDAO extends BaseSQLDAO<LogRequestUri> {

	public LogRequestUriDAO() {
		super(LogRequestUri.class);
	}
	
	public LogRequestUri save(String requestUri) {
		LogRequestUri uri = findByField("requestUri", requestUri);
		if(uri == null) {
			LogRequestUri newUri = new LogRequestUri();
			newUri.setRequestUri(requestUri);
			return persist(newUri);
		} else {
			return uri;
		}
	}
}
