package com.payroom.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payroom.dao.PaymentDAO;
import com.payroom.model.Payment;

@Repository
public class PaymentDAOImpl implements PaymentDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Payment> findPaymentsList() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Payment> query = currentSession.createQuery("from Payment", Payment.class);

		List<Payment> payment = query.getResultList();

		return payment;

	}

	@Override
	public Payment findPaymentById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Payment payment = currentSession.get(Payment.class, id);

		return payment;
	}

	@Override
	public int savePayment(Payment payment) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(payment);

		return payment.getId();

	}

	@Override
	public void deletePayment(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Payment> query = currentSession.createQuery("delete from Payment where id=:idPayment");

		query.setParameter("idPayment", id);
		query.executeUpdate();

	}

}
