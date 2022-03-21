package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.RoomUserDAO;
import com.payroom.model.Room;
import com.payroom.model.RoomUser;
import com.payroom.model.User;
import com.payroom.service.RoomUserService;

@Service
@Transactional
public class RoomUserServiceImpl implements RoomUserService {

	@Autowired
	private RoomUserDAO roomUserDAO;

	@Override
	public List<RoomUser> getRoomsUsersList() {
		List<RoomUser> roomUsers = roomUserDAO.getRoomsUsersList();
		return roomUsers;
	}

	@Override
	public RoomUser getRoomUserById(int id) {
		RoomUser roomUser = roomUserDAO.getRoomUserById(id);
		return roomUser;
	}

	@Override
	public RoomUser getRoomUserByUserRoom(User user, Room room) {
		RoomUser roomUser = roomUserDAO.getRoomUserByUserRoom(user, room);
		return roomUser;
	}

	@Override
	public int saveRoomUser(RoomUser roomUser) {
		roomUserDAO.saveRoomUser(roomUser);
		return roomUser.getId();
	}

	@Override
	public void deleteRoomUser(int id) {
		roomUserDAO.deleteRoomUser(id);

	}
}
