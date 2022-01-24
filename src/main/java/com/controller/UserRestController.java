package com.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import com.model.Avatar;
import com.model.Expense;
import com.model.Room;
import com.model.User;
import com.modelDtos.Response;
import com.modelDtos.UserDto;
import com.modelDtos.UserResponse;
import com.security.JsonWebTokenService;
import com.service.AvatarService;
import com.service.UserService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/api/v1.0")

public class UserRestController {

	@Autowired
	private UserService usersService;

	@Autowired
	private AvatarService avatarsService;

	@Autowired
	JsonWebTokenService jsonWebTokenService;

	@GetMapping("/users")
	public List<User> getUsersList() {

		return usersService.findUsersList();
	}

	@GetMapping("/users/list/{username}")
	public List<User> getUsersByUsername(@PathVariable String username) {

		List<User> users = usersService.findUsersByUsername(username);

		return users;
	}

	@GetMapping("/server")
	public String server() {

		return "Servidor 1";
	}

	@GetMapping("/users/{userId}")
	public User getUserById(@PathVariable int userId) {
		User user = usersService.findUserById(userId);

		if (user == null) {
			throw new RuntimeException("Expense id not found -" + userId);
		}
		return user;
	}

	@GetMapping("/users/{userId}/rooms")
	public List<Room> getUserRooms(@PathVariable int userId) {
		User user = usersService.findUserById(userId);

		if (user == null) {
			throw new RuntimeException("User id not found -" + userId);
		}
		List<Room> rooms = new ArrayList<>();
		List<Room> favorites = user.getFavoriteRooms();
		for (Room favorite : favorites) {
			favorite.setIsFavorite(true);
		}
		rooms.addAll(user.getRooms());
		rooms.addAll(favorites);

		Collections.reverse(rooms);

		return rooms;
	}

	@GetMapping("/users/{userId}/expenses")
	public List<Expense> getUserExpenses(@PathVariable int userId) {
		User user = usersService.findUserById(userId);

		if (user == null) {
			throw new RuntimeException("User id not found -" + userId);
		}
		List<Expense> expenses = new ArrayList<>();
		expenses.addAll(user.getExpenses());

		return expenses;
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody UserDto log) {
		UserResponse jwt = new UserResponse(0, null, null);
		User user = usersService.findUserByUsername(log.getUsername());
		if (user == null) {
			Response response = new Response("User dont find", "Usuario Inexistente");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			String pass = user.getPassword();
			jwt.setUsername(user.getUsername());
			if (log.getPassword().equals(pass)) {
				String token = jsonWebTokenService.generateJWTToken("" + user.getId());
				jwt.setToken(token);
				jwt.setId(user.getId());
				return new ResponseEntity<>(jwt, HttpStatus.OK);
			} else {
				Response response = new Response("Password not match", "Contraseña incorrecta");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

		}

	}

	@PostMapping("/users")
	public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
		try {
			User user = usersService.findUserByUsername(userDto.getUsername());
			if (user == null) {
				Avatar avatar = avatarsService.findAvatarById(userDto.getAvatar());
				user = new User(userDto.getName(), userDto.getLastname(), userDto.getUsername(), userDto.getEmail(),
						userDto.getPassword(), avatar);

				int id = usersService.saveUser(user);
				String token = jsonWebTokenService.generateJWTToken("" + user.getId());
				UserResponse response = new UserResponse(id, user.getUsername(), token);

				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				Response failResponse = new Response("Duplicated user", "El nombre de usuario ya existe");
				return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@PutMapping("/users")
	public ResponseEntity<Object> updateUser(HttpServletRequest request, @RequestBody UserDto userDto) {

		try {
			int userPayload = jsonWebTokenService.validateUserJWT(request);
			if (userPayload == userDto.getId()) {
				User user = usersService.findUserById(userDto.getId());
				user.setName(userDto.getName());

				usersService.saveUser(user);

				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				Response response = new Response("Corrupted token, User Id mismatch", "Cambio no permitido");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@DeleteMapping("/users")
	public ResponseEntity<Object> deteteUser(HttpServletRequest request) {

		try {
			int userPayload = jsonWebTokenService.validateUserJWT(request);
			User user = usersService.findUserById(userPayload);
			if (user == null) {
				Response response = new Response("Data not foun", "No se encuenta el usuario en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (user.getId() == userPayload) {
				user.setState(2);
				user.setDate(new Date());
				usersService.saveUser(user);
				Response response = new Response("User state change", "Usuario dado de baja");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				Response response = new Response("User Id mismatch", "No tiene autorizacion para realizar esta accion");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

}