package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.UserDAO;
import com.payroom.model.User;
import com.payroom.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO usersDAO;

	@Override
	public List<User> getUsersList() {
		List<User> listUsers = usersDAO.getUsersList();
		return listUsers;
	}

	@Override
	public User getUserById(int id) {
		User user = usersDAO.getUserById(id);
		return user;
	}

	@Override
	public User getUserByUsername(String usr) {
		User user = usersDAO.getUserByUsername(usr);
		return user;
	}

	@Override
	public User getUserByGoogleId(String usr) {
		User user = usersDAO.getUserByGoogleId(usr);
		return user;
	}

	@Override
	public List<User> getUsersByUsername(String usr) {
		List<User> listUsers = usersDAO.getUsersByUsername(usr);
		return listUsers;
	}

	@Override
	public int saveUser(User user) {
		usersDAO.saveUser(user);
		return user.getId();

	}

	@Override
	public void deleteUserById(int id) {
		usersDAO.deleteUser(id);
	}

}
