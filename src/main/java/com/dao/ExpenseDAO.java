package com.dao;

import java.util.List;

import com.model.Expense;

public interface ExpenseDAO {

	public List<Expense> findExpensesList();

	public Expense findExpenseById(int id);

	public int saveExpense(Expense expense);

	public void deleteExpense(int id);

}
