package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.CoverpageDAO;
import com.payroom.model.Coverpage;

@Repository
public class CoverpageDAOImpl implements CoverpageDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Coverpage> getCoverpagesList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Coverpage> query = currentSession.createQuery("from Coverpage where isStock = true", Coverpage.class);

		List<Coverpage> coverpage = query.getResultList();

		return coverpage;

	}

	@Override
	public Coverpage getCoverpageById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Coverpage coverpage = currentSession.get(Coverpage.class, id);

		return coverpage;
	}

	@Override
	public int saveCoverpage(Coverpage coverpage) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(coverpage);

		return coverpage.getId();
	}

	@Override
	public void deleteCoverpage(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Coverpage> query = currentSession.createQuery("delete from Coverpage where id=:idCoverpage");

		query.setParameter("idCoverpage", id);
		query.executeUpdate();

	}

}
