package com.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.RoomLogDAO;
import com.model.Room;
import com.model.RoomLog;
import com.model.User;

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
