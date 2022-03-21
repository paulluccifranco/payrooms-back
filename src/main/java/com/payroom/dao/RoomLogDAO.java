package com.payroom.dao;

import java.util.List;

import com.payroom.model.Room;
import com.payroom.model.RoomLog;
import com.payroom.model.User;

public interface RoomLogDAO {

	public List<RoomLog> getRoomLogsByRoom(Room room);

	public List<RoomLog> getRoomLogsByUser(User user);

	public int saveRoomLog(RoomLog roomLog);

}
