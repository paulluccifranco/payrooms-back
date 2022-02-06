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
	public List<User> findUsersList() {
		List<User> listUsers = usersDAO.findUsersList();
		return listUsers;
	}

	@Override
	public User findUserById(int id) {
		User user = usersDAO.findUserById(id);
		return user;
	}

	@Override
	public User findUserByUsername(String usr) {
		User user = usersDAO.findUserByUsername(usr);
		return user;
	}

	@Override
	public List<User> findUsersByUsername(String usr) {
		List<User> listUsers = usersDAO.findUsersByUsername(usr);
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
