package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.payroom.dao.RoomLogDAO;
import com.payroom.model.Room;
import com.payroom.model.RoomLog;
import com.payroom.model.User;

public class RoomLogDAOImpl implements RoomLogDAO {

	@Autowired
	EntityManager entityManager;

	public List<RoomLog> findRoomLogsByRoom(Room room) {

		return null;

	}

	public List<RoomLog> findRoomLogsByUser(User user) {

		return null;
	}

	public int saveRoomLog(RoomLog roomLog) {

		return 0;
	}

}
