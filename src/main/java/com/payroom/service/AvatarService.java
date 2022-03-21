package com.payroom.service;

import java.util.List;

import com.payroom.model.Avatar;

public interface AvatarService {

	public List<Avatar> getAvatarsList();

	public Avatar getAvatarById(int id);

	public void saveAvatar(Avatar avatar);

	public void deleteAvatarById(int id);

}
