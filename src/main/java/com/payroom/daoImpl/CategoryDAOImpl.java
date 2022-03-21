package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.CategoryDAO;
import com.payroom.model.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Category> getCategoriesList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Category> query = currentSession.createQuery("from Category", Category.class);

		List<Category> category = query.getResultList();

		return category;

	}

	@Override
	public Category getCategoryById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Category category = currentSession.get(Category.class, id);

		return category;
	}

	@Override
	public void saveCategory(Category category) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(category);

	}

	@Override
	public void deleteCategory(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Category> query = currentSession.createQuery("delete from Category where id=:idCategory");

		query.setParameter("idCategory", id);
		query.executeUpdate();

	}

}
