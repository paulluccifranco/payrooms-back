package com.service;

import java.util.List;

import com.model.Expense;

public interface ExpenseService {

	public List<Expense> findExpenseList();

	public Expense findExpenseById(int id);

	public int saveExpense(Expense expense);

	public void deleteExpense(int id);

}
