package com.payroom.dao;

import java.util.List;

import com.payroom.model.Expense;

public interface ExpenseDAO {

	public List<Expense> getExpensesList();

	public Expense getExpenseById(int id);

	public int saveExpense(Expense expense);

	public void deleteExpense(int id);

}
