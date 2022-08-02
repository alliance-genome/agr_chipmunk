package org.alliancegenome.agr_submission.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.LoggedInUser;

@ApplicationScoped
public class UserDAO extends BaseSQLDAO<LoggedInUser> {

	public UserDAO() {
		super(LoggedInUser.class);
	}
	
	@Transactional
	public LoggedInUser findUserByApiKey(String apiKey) {
		return findByField("apiKey", apiKey);
	}

}
