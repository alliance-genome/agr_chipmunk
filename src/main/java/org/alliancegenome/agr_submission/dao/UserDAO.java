package org.alliancegenome.agr_submission.dao;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.LoggedInUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

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
