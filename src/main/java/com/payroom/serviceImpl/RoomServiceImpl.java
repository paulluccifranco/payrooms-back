package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.RoomDAO;
import com.payroom.model.Room;
import com.payroom.service.RoomService;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDAO roomDAO;

	@Override
	public List<Room> findRoomsList() {
		List<Room> listUsers = roomDAO.findRoomsList();
		return listUsers;
	}

	@Override
	public Room findRoomById(int id) {
		Room user = roomDAO.findRoomById(id);
		return user;
	}

	@Override
	public int saveRoom(Room room) {
		roomDAO.saveRoom(room);
		return room.getId();
	}

	@Override
	public void deleteRoom(int id) {
		roomDAO.deleteRoom(id);
	}

}
