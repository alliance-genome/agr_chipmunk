package org.alliancegenome.agr_submission.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.alliancegenome.agr_submission.BaseController;
import org.alliancegenome.agr_submission.entities.User;
import org.alliancegenome.agr_submission.interfaces.server.UserControllerInterface;
import org.alliancegenome.agr_submission.services.UserService;

@RequestScoped
public class UserController extends BaseController implements UserControllerInterface {

	@Inject UserService userService;
	
	@Override
	public User create(User entity) {
		return userService.create(entity);
	}

	@Override
	public User get(Long id) {
		return userService.get(id);
	}

	@Override
	public User update(User entity) {
		return userService.update(entity);
	}

	@Override
	public User delete(Long id) {
		return userService.delete(id);
	}
	
	public List<User> getUsers() {
		return userService.getUsers();
	}


}
