package com.payroom.service;

import java.util.List;

import com.payroom.model.Room;
import com.payroom.model.RoomUser;
import com.payroom.model.User;

public interface RoomUserService {

	public List<RoomUser> getRoomsUsersList();

	public RoomUser getRoomUserById(int id);

	public RoomUser getRoomUserByUserRoom(User user, Room room);

	public List<RoomUser> getRoomUserByRoom(Room room);

	public int saveRoomUser(RoomUser roomUser);

	public void deleteRoomUser(int id);

}
