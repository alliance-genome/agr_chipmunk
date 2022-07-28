package org.alliancegenome.agr_submission.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.dao.UserDAO;
import org.alliancegenome.agr_submission.entities.LoggedInUser;
import org.alliancegenome.agr_submission.util.AESUtil;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
@RequestScoped
public class UserService extends BaseService<LoggedInUser> {
	
	@Inject UserDAO dao;

	@Override
	@Transactional
	public LoggedInUser create(LoggedInUser entity) {
		log.info("UserService: create: ");
		try {
			entity.setApiKey(AESUtil.encrypt(entity.getName(), ConfigHelper.getEncryptionPasswordKey()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao.persist(entity);
	}

	@Override
	@Transactional
	public LoggedInUser get(Long id) {
		log.info("UserService: get: " + id);
		return dao.find(id);
	}

	@Override
	@Transactional
	public LoggedInUser update(LoggedInUser entity) {
		LoggedInUser u = get(entity.getId());
		u.setApiKey(entity.getApiKey());
		u.setName(entity.getName());
		u.setPassword(entity.getPassword());
		u.setUsername(entity.getUsername());
		log.info("UserService: update: ");
		return dao.merge(u);
	}

	@Override
	@Transactional
	public LoggedInUser delete(Long id) {
		log.info("UserService: delete: " + id);
		return dao.remove(id);
	}

	public List<LoggedInUser> getUsers() {
		return dao.findAll();
	}
}
