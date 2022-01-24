package com.dao;

import java.util.List;

import com.model.Room;
import com.model.RoomLog;
import com.model.User;

public interface RoomLogDAO {

	public List<RoomLog> findRoomLogsByRoom(Room room);

	public List<RoomLog> findRoomLogsByUser(User user);

	public int saveRoomLog(RoomLog roomLog);

}
