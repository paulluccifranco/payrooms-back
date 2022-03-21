package com.payroom.dao;

import java.util.List;

import com.payroom.model.Avatar;

public interface AvatarDAO {

	public List<Avatar> getAvatarsList();

	public Avatar getAvatarById(int id);

	public void saveAvatar(Avatar avatar);

	public void deleteAvatar(int id);

}
