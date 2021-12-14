package com.service;

import java.util.List;

import com.model.User;

public interface UserService {

	public List<User> findUsersList();

	public User findUserById(int id);

	public User findUserByUsername(String usr);

	public int saveUser(User user);

	public void deleteUserById(int id);

}
