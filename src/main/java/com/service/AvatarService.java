package com.service;

import java.util.List;

import com.model.Avatar;

public interface AvatarService {

	public List<Avatar> findAvatarsList();

	public Avatar findAvatarById(int id);

	public void saveAvatar(Avatar avatar);

	public void deleteAvatarById(int id);

}
