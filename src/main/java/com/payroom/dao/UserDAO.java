package com.payroom.dao;

import java.util.List;

import com.payroom.model.User;

public interface UserDAO {

	public List<User> getUsersList();

	public User getUserById(int id);

	public User getUserByUsername(String usrn);

	public User getUserByGoogleId(String usrn);

	public List<User> getUsersByUsername(String usrn);

	public int saveUser(User user);

	public void deleteUser(int id);

}
