package com.payroom.daoImpl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.PropertyDAO;
import com.payroom.model.Property;

@Repository
public class PropertyDAOImpl implements PropertyDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public String getPropertyByName(String name) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Property> query = currentSession.createQuery("from Property WHERE name=:name", Property.class);
		query.setParameter("name", name);
		Property property = query.uniqueResult();

		return property.getValue();

	}

}
