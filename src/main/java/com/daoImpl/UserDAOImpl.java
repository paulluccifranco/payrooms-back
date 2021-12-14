package com.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.UserDAO;
import com.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<User> findUsersList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> query = currentSession.createQuery("from User", User.class);

		List<User> users = query.getResultList();

		return users;

	}

	@Override
	public User findUserByUsername(String usrn) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<User> query = currentSession.createQuery("from User WHERE username=:usrn", User.class);
		query.setParameter("usrn", usrn);
		User user = query.uniqueResult();

		return user;

	}

	@Override
	public User findUserById(int id) {
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
