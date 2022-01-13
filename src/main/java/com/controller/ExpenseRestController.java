package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Expense;
import com.model.Room;
import com.model.User;
import com.modelJson.ExpenseDto;
import com.modelJson.UserResponse;
import com.service.ExpenseService;
import com.service.RoomService;
import com.service.UserService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST })
@RequestMapping("/api/v1.0")

public class ExpenseRestController {

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@GetMapping("/expenses")
	public List<Expense> findAll() {
		return expenseService.findExpenseList();
	}

	@GetMapping("/expenses/{expenseId}")
	public Expense getExpense(@PathVariable int expenseId) {
		Expense expense = expenseService.findExpenseById(expenseId);

		if (expense == null) {
			throw new RuntimeException("Expense id not found -" + expenseId);
		}
		return expense;
	}

	@PostMapping("/expenses")
	public UserResponse addExpense(@RequestBody ExpenseDto expenseDto) {
		UserResponse response = new UserResponse(0, "No se pudo crear la sala", null);
		Room room = roomService.findRoomById(expenseDto.getIdRoom());
		User user = userService.findUserById(expenseDto.getIdUser());

		if (user == null || room == null) {
			return response;
		}

		Expense expense = new Expense(expenseDto.getDescription(), expenseDto.getValue(), user, room,
				expenseDto.getParticipants());
		expense.setId(0);

		int id = expenseService.saveExpense(expense);
		response.setId(id);
		response.setUsername("Gasto Creado");

		return response;

	}

	@PutMapping("/expenses")
	public Expense updateExpense(@RequestBody Expense expense) {

		expenseService.saveExpense(expense);

		return expense;
	}

	@DeleteMapping("expenses/{expenseId}")
	public String deteteExpense(@PathVariable int expenseId) {

		Expense expense = expenseService.findExpenseById(expenseId);

		if (expense == null) {
			throw new RuntimeException("Expense id not found -" + expenseId);
		}

		expenseService.deleteExpense(expenseId);

		return "Deleted expense id - " + expenseId;
	}

}