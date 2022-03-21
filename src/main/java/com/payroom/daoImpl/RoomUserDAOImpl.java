package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.RoomUserDAO;
import com.payroom.model.Room;
import com.payroom.model.RoomUser;
import com.payroom.model.User;

@Repository
public class RoomUserDAOImpl implements RoomUserDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<RoomUser> getRoomsUsersList() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<RoomUser> query = currentSession.createQuery("from RoomUser", RoomUser.class);
		List<RoomUser> roomsUsers = query.getResultList();
		return roomsUsers;
	}

	@Override
	public RoomUser getRoomUserById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		RoomUser roomUser = currentSession.get(RoomUser.class, id);
		return roomUser;
	}

	@Override
	public RoomUser getRoomUserByUserRoom(User user, Room room) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<RoomUser> query = currentSession.createQuery("from RoomUser WHERE room=:room and user=:user",
				RoomUser.class);
		query.setParameter("room", room);
		query.setParameter("user", user);
		RoomUser roomUser = query.uniqueResult();

		return roomUser;
	}

	@Override
	public int saveRoomUser(RoomUser roomUser) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(roomUser);
		return roomUser.getId();
	}

	@Override
	public void deleteRoomUser(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<RoomUser> query = currentSession.createQuery("delete from RoomUser where id =:id");
		query.setParameter("id", id);
		query.executeUpdate();

	}
}
