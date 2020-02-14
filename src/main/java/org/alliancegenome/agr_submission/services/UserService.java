package org.alliancegenome.agr_submission.services;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.dao.UserDAO;
import org.alliancegenome.agr_submission.entities.User;
import org.alliancegenome.agr_submission.util.AESUtil;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class UserService extends BaseService<User> {
	
	@Inject
	private UserDAO dao;

	@Override
	@Transactional
	public User create(User entity) {
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
	public User get(Long id) {
		log.info("UserService: get: " + id);
		return dao.find(id);
	}

	@Override
	@Transactional
	public User update(User entity) {
		User u = get(entity.getId());
		u.setApiKey(entity.getApiKey());
		u.setName(entity.getName());
		u.setPassword(entity.getPassword());
		u.setUsername(entity.getUsername());
		log.info("UserService: update: ");
		return dao.merge(u);
	}

	@Override
	@Transactional
	public User delete(Long id) {
		log.info("UserService: delete: " + id);
		return dao.remove(id);
	}

	public List<User> getUsers() {
		return dao.findAll();
	}
}
