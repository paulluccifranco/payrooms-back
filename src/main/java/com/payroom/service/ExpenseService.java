package com.payroom.service;

import java.util.List;

import com.payroom.model.Expense;

public interface ExpenseService {

	public List<Expense> getExpenseList();

	public Expense getExpenseById(int id);

	public int saveExpense(Expense expense);

	public void deleteExpense(int id);

}
