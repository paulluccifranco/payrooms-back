package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.ExpenseDAO;
import com.payroom.model.Expense;
import com.payroom.service.ExpenseService;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseDAO expenseDAO;

	@Override
	public List<Expense> getExpenseList() {
		List<Expense> listUsers = expenseDAO.getExpensesList();
		return listUsers;
	}

	@Override
	public Expense getExpenseById(int id) {
		Expense user = expenseDAO.getExpenseById(id);
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
