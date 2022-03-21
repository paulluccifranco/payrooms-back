package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.RoomDAO;
import com.payroom.model.Room;

@Repository
public class RoomDAOImpl implements RoomDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Room> getRoomsList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Room> query = currentSession.createQuery("from Room where active = 1", Room.class);

		List<Room> room = query.getResultList();

		return room;
	}

	@Override
	public Room getRoomById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Room room = currentSession.get(Room.class, id);

		return room;
	}

	@Override
	public int saveRoom(Room room) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(room);

		return room.getId();
	}

	@Override
	public void deleteRoom(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Room> query = currentSession.createQuery("delete from Room where id=:idRoom");

		query.setParameter("idRoom", id);
		query.executeUpdate();
	}
}
