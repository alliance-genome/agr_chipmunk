package org.alliancegenome.agr_submission.services;

import java.util.*;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.UserDAO;
import org.alliancegenome.agr_submission.entities.LoggedInUser;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
			entity.setApiKey(UUID.randomUUID().toString());
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
