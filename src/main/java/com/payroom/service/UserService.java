package com.payroom.service;

import java.util.List;

import com.payroom.model.User;

public interface UserService {

	public List<User> getUsersList();

	public User getUserById(int id);

	public User getUserByUsername(String usr);

	public User getUserByGoogleId(String usr);

	public List<User> getUsersByUsername(String usr);

	public int saveUser(User user);

	public void deleteUserById(int id);

}
