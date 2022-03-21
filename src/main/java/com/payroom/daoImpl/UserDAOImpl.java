package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.UserDAO;
import com.payroom.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<User> getUsersList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> query = currentSession.createQuery("from User", User.class);

		List<User> users = query.getResultList();

		return users;

	}

	@Override
	public User getUserByUsername(String usrn) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<User> query = currentSession.createQuery("from User WHERE username=:usrn", User.class);
		query.setParameter("usrn", usrn);
		User user = query.uniqueResult();

		return user;

	}

	@Override
	public List<User> getUsersByUsername(String usrn) {
		Session currentSession = entityManager.unwrap(Session.class);
		usrn = usrn + "%";

		Query<User> query = currentSession.createQuery("from User WHERE username LIKE :usrn", User.class);
		query.setParameter("usrn", usrn);

		List<User> users = query.getResultList();

		return users;

	}

	@Override
	public User getUserByGoogleId(String gid) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<User> query = currentSession.createQuery("from User WHERE googleId=:gid", User.class);
		query.setParameter("gid", gid);
		User user = query.uniqueResult();

		return user;

	}

	@Override
	public User getUserById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		User user = currentSession.get(User.class, id);

		return user;
	}

	@Override
	public int saveUser(User user) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(user);

		return user.getId();

	}

	@Override
	public void deleteUser(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> query = currentSession.createQuery("delete from User where id=:idUser");

		query.setParameter("idUser", id);
		query.executeUpdate();

	}

}
