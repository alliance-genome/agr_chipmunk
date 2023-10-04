package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.LoggedInUser;
import org.alliancegenome.agr_submission.interfaces.server.UserControllerInterface;
import org.alliancegenome.agr_submission.services.UserService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UserController extends BaseController implements UserControllerInterface {

	@Inject UserService userService;
	
	@Override
	public LoggedInUser create(LoggedInUser entity) {
		return userService.create(entity);
	}

	@Override
	public LoggedInUser get(Long id) {
		return userService.get(id);
	}

	@Override
	public LoggedInUser update(LoggedInUser entity) {
		return userService.update(entity);
	}

	@Override
	public LoggedInUser delete(Long id) {
		return userService.delete(id);
	}
	
	public List<LoggedInUser> getUsers() {
		return userService.getUsers();
	}


}
