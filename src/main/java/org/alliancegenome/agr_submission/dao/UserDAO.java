package org.alliancegenome.agr_submission.dao;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.User;

@ApplicationScoped
public class UserDAO extends BaseSQLDAO<User> {

	public UserDAO() {
		super(User.class);
	}

}
