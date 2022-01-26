package com.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.dao.RoomLogDAO;
import com.model.Category;
import com.model.Expense;
import com.model.Room;
import com.model.RoomLog;
import com.model.User;
import com.modelDtos.ExpenseDto;
import com.modelDtos.LogRoomType;
import com.modelDtos.Response;
import com.security.JsonWebTokenService;
import com.service.CategoryService;
import com.service.ExpenseService;
import com.service.RoomService;
import com.service.UserService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
		RequestMethod.DELETE })
@RequestMapping("/api/v1.0")

public class ExpenseRestController {

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@Autowired
	JsonWebTokenService jsonWebTokenService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	RoomLogDAO roomLogDAO;

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
	public ResponseEntity<Object> addExpense(@RequestBody ExpenseDto expenseDto) {
		try {
			Room room = roomService.findRoomById(expenseDto.getIdRoom());
			User user = userService.findUserById(expenseDto.getIdUser());

			if (user == null || room == null) {
				Response response = new Response("Data not found", "Los datos no fueron encontrados");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			Category category = categoryService.findCategoryById(expenseDto.getCategory());

			Expense expense = new Expense(expenseDto.getDescription(), expenseDto.getValue(), user, room,
					expenseDto.getParticipants(), expenseDto.getDate(), category);
			expense.setId(0);

			int id = expenseService.saveExpense(expense);
			RoomLog roomLog = new RoomLog(room, user, new Date(), LogRoomType.EXPENSE_ADDED, id);

			roomLogDAO.saveRoomLog(roomLog);

			Response response = new Response("Expense created id: " + id, "El gasto fue creado con exito");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	@PutMapping("/expenses")
	public ResponseEntity<Object> updateExpense(HttpServletRequest request, @RequestBody ExpenseDto expenseDto) {
		try {
			// int userId = jsonWebTokenService.validateUserJWT(request);
			Expense expense = expenseService.findExpenseById(expenseDto.getId());
			if (expense == null) {
				Response response = new Response("Expense not found", "El gasto no se encuentra");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			Category category = categoryService.findCategoryById(expenseDto.getCategory());
			expense.setDescription(expenseDto.getDescription());
			expense.setValue(expenseDto.getValue());
			expense.setDate(expenseDto.getDate());
			expense.setParticipants(expenseDto.getParticipants());
			expense.setCategory(category);
			expenseService.saveExpense(expense);

			RoomLog roomLog = new RoomLog(expense.getRoom(), expense.getUser(), new Date(), LogRoomType.EXPENSE_UPDATED,
					expense.getId());
			roomLogDAO.saveRoomLog(roomLog);

			Response response = new Response("Expense edited", "El gasto fue editado con exito");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	@DeleteMapping("expenses/{expenseId}")
	public ResponseEntity<Object> deteteExpense(HttpServletRequest request, @PathVariable int expenseId) {
		try {
			int userId = jsonWebTokenService.validateUserJWT(request);
			Expense expense = expenseService.findExpenseById(expenseId);

			if (expense == null) {
				Response response = new Response("Expense not found", "El gasto no existe");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				if (userId == expense.getUserId()) {
					expenseService.deleteExpense(expenseId);
					RoomLog roomLog = new RoomLog(expense.getRoom(), expense.getUser(), new Date(),
							LogRoomType.EXPENSE_DELETED, expense.getId());
					roomLogDAO.saveRoomLog(roomLog);

					Response response = new Response("Espense deleted", "El gasto fue dado de baja");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					Response response = new Response("User Id mismatch",
							"No tiene autorizacion para realizar esta accion");
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				}

			}

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

}