package com.dao;

import java.util.List;

import com.model.Avatar;

public interface AvatarDAO {

	public List<Avatar> findAvatarsList();

	public Avatar findAvatarById(int id);

	public void saveAvatar(Avatar avatar);

	public void deleteAvatar(int id);

}
