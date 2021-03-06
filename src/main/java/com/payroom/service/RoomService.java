package com.payroom.service;

import java.util.List;

import com.payroom.model.Room;

public interface RoomService {

	public List<Room> getRoomsList();

	public Room getRoomById(int id);

	public int saveRoom(Room room);

	public void deleteRoom(int id);

}
