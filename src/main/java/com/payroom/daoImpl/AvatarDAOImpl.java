package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.AvatarDAO;
import com.payroom.model.Avatar;

@Repository
public class AvatarDAOImpl implements AvatarDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Avatar> getAvatarsList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Avatar> query = currentSession.createQuery("from Avatar", Avatar.class);

		List<Avatar> avatar = query.getResultList();

		return avatar;

	}

	@Override
	public Avatar getAvatarById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Avatar avatar = currentSession.get(Avatar.class, id);

		return avatar;
	}

	@Override
	public void saveAvatar(Avatar avatar) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(avatar);

	}

	@Override
	public void deleteAvatar(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Avatar> query = currentSession.createQuery("delete from Avatar where id=:idAvatar");

		query.setParameter("idAvatar", id);
		query.executeUpdate();

	}

}
