package com.payroom.service;

import java.util.List;

import com.payroom.model.Avatar;

public interface AvatarService {

	public List<Avatar> findAvatarsList();

	public Avatar findAvatarById(int id);

	public void saveAvatar(Avatar avatar);

	public void deleteAvatarById(int id);

}
