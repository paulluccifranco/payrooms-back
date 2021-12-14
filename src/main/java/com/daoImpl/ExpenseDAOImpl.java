package com.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.ExpenseDAO;
import com.model.Expense;

@Repository
public class ExpenseDAOImpl implements ExpenseDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Expense> findExpensesList() {
		Session currentSession = entityManager.unwrap(Session.class);
		// puto el que lee
		Query<Expense> query = currentSession.createQuery("from Expense", Expense.class);

		List<Expense> expense = query.getResultList();

		return expense;

	}

	@Override
	public Expense findExpenseById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Expense expense = currentSession.get(Expense.class, id);

		return expense;
	}

	@Override
	public int saveExpense(Expense expense) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(expense);

		return expense.getId();

	}

	@Override
	public void deleteExpense(int id) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Expense> query = currentSession.createQuery("delete from Expense where id=:idExpense");

		query.setParameter("idExpense", id);
		query.executeUpdate();

	}

}
