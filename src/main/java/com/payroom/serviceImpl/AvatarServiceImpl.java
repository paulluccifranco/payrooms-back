package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.AvatarDAO;
import com.payroom.model.Avatar;
import com.payroom.service.AvatarService;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

	@Autowired
	private AvatarDAO avatarsDAO;

	@Override
	public List<Avatar> findAvatarsList() {
		List<Avatar> listAvatars = avatarsDAO.findAvatarsList();
		return listAvatars;
	}

	@Override
	public Avatar findAvatarById(int id) {
		Avatar avatar = avatarsDAO.findAvatarById(id);
		return avatar;
	}

	@Override
	public void saveAvatar(Avatar avatar) {
		avatarsDAO.saveAvatar(avatar);

	}

	@Override
	public void deleteAvatarById(int id) {
		avatarsDAO.deleteAvatar(id);
	}

}
