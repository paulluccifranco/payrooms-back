package com.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.modelDtos.Response;
import com.security.JsonWebTokenService;
import com.service.UserService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/api/v1.0")

public class FriendRestController {

	@Autowired
	private UserService usersService;

	@Autowired
	JsonWebTokenService jsonWebTokenService;

	@GetMapping("/friends/{userId}")
	public List<User> getUserRooms(@PathVariable int userId) {
		User user = usersService.findUserById(userId);

		if (user == null) {
			throw new RuntimeException("User id not found -" + userId);
		}
		List<User> friends = new ArrayList<>();
		friends.addAll(user.getFriends());

		return friends;
	}

	@PostMapping("/friends/{userId}/{friendId}")
	public ResponseEntity<Object> addFriend(HttpServletRequest request, @PathVariable int userId,
			@PathVariable int friendId) {

		try {
			int userPayload = jsonWebTokenService.validateUserJWT(request);
			if (userPayload == userId) {
				User user = usersService.findUserById(userId);
				User friend = usersService.findUserById(friendId);

				user.addFriend(friend);

				usersService.saveUser(user);
				Response response = new Response("Relation created", "Usuario agregado a lista de amigos");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				Response response = new Response("Corrupted token, User Id mismatch", "Cambio no permitido");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@DeleteMapping("friends/{userId}/{friendId}")
	public ResponseEntity<Object> deteteExpense(HttpServletRequest request, @PathVariable int userId,
			@PathVariable int friendId) {

		try {
			int userPayload = jsonWebTokenService.validateUserJWT(request);
			if (userId == userPayload || friendId == userPayload) {
				User user = usersService.findUserById(userId);
				User friend = usersService.findUserById(friendId);

				if (user == null || friend == null) {
					Response response = new Response("Data not found",
							"No se encuenta el/los usuario/s en la base de datos");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				Boolean deleted = false;
				if (user.getFriends().remove(friend)) {
					deleted = true;
				} else if (friend.getFriends().remove(user)) {
					deleted = true;
				}

				if (deleted) {
					usersService.saveUser(user);
					Response response = new Response("Relation deleted", "Los usuarios ya no son amigos");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					Response response = new Response("Relation unexist", "Los usuarios no tienen relacion");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}

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