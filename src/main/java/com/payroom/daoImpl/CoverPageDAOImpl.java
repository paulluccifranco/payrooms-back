package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.CoverPageDAO;
import com.payroom.model.CoverPage;

@Repository
public class CoverPageDAOImpl implements CoverPageDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<CoverPage> findCoverPagesList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<CoverPage> query = currentSession.createQuery("from CoverPage", CoverPage.class);

		List<CoverPage> coverPage = query.getResultList();

		return coverPage;

	}

	@Override
	public CoverPage findCoverPageById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		CoverPage coverPage = currentSession.get(CoverPage.class, id);

		return coverPage;
	}

	@Override
	public void saveCoverPage(CoverPage coverPage) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(coverPage);

	}

	@Override
	public void deleteCoverPage(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<CoverPage> query = currentSession.createQuery("delete from CoverPage where id=:idCoverPage");

		query.setParameter("idCoverPage", id);
		query.executeUpdate();

	}

}
