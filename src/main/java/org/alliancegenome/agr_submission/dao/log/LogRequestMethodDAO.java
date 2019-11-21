package org.alliancegenome.agr_submission.dao.log;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.log.LogRequestMethod;

@ApplicationScoped
public class LogRequestMethodDAO extends BaseSQLDAO<LogRequestMethod> {
	
	public LogRequestMethodDAO() {
		super(LogRequestMethod.class);
	}
	
	public LogRequestMethod save(String requestMethod) {
		LogRequestMethod method = findByField("requestMethod", requestMethod);
		if(method == null) {
			LogRequestMethod newMethod = new LogRequestMethod();
			newMethod.setRequestMethod(requestMethod);
			return persist(newMethod);
		} else {
			return method;
		}
	}
}
