package com.payroom.dao;

import java.util.List;

import com.payroom.model.Room;
import com.payroom.model.RoomLog;
import com.payroom.model.User;

public interface RoomLogDAO {

	public List<RoomLog> findRoomLogsByRoom(Room room);

	public List<RoomLog> findRoomLogsByUser(User user);

	public int saveRoomLog(RoomLog roomLog);

}
