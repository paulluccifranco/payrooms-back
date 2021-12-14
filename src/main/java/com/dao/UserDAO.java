package com.dao;

import java.util.List;

import com.model.User;

public interface UserDAO {

	public List<User> findUsersList();

	public User findUserById(int id);

	public User findUserByUsername(String usrn);

	public int saveUser(User user);

	public void deleteUser(int id);

}
