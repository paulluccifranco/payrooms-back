package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ExpenseDAO;
import com.model.Expense;
import com.service.ExpenseService;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseDAO expenseDAO;

	@Override
	public List<Expense> findExpenseList() {
		List<Expense> listUsers = expenseDAO.findExpensesList();
		return listUsers;
	}

	@Override
	public Expense findExpenseById(int id) {
		Expense user = expenseDAO.findExpenseById(id);
		return user;
	}

	@Override
	public int saveExpense(Expense expense) {
		expenseDAO.saveExpense(expense);

		return expense.getId();

	}

	@Override
	public void deleteExpense(int id) {
		expenseDAO.deleteExpense(id);
	}

}
