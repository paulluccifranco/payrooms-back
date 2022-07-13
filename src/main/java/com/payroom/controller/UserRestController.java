package com.payroom.controller;

import java.util.ArrayList;
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

import com.payroom.model.Avatar;
import com.payroom.model.Expense;
import com.payroom.model.Room;
import com.payroom.model.RoomUser;
import com.payroom.model.User;
import com.payroom.modelDtos.GoogleUserDto;
import com.payroom.modelDtos.Response;
import com.payroom.modelDtos.RoomUserDto;
import com.payroom.modelDtos.SocialUserDto;
import com.payroom.modelDtos.UserDto;
import com.payroom.modelDtos.UserResponse;
import com.payroom.modelDtos.UserRoomDto;
import com.payroom.security.GoogleWebService;
import com.payroom.security.JsonWebTokenService;
import com.payroom.service.AvatarService;
import com.payroom.service.UserService;

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
	private JsonWebTokenService jsonWebTokenService;

	@Autowired
	private GoogleWebService googleService;

	@GetMapping("/users")
	public List<User> getUsersList() {

		return usersService.getUsersList();
	}

	@GetMapping("/users/list/{username}")
	public List<User> getUsersByUsername(@PathVariable String username) {

		List<User> users = usersService.getUsersByUsername(username);

		return users;
	}

	@GetMapping("/server")
	public String server() {

		return "Servidor 1";
	}

	@GetMapping("/users/{userId}")
	public User getUserById(@PathVariable int userId) {
		User user = usersService.getUserById(userId);

		if (user == null) {
			throw new RuntimeException("Expense id not found -" + userId);
		}
		return user;
	}

	@GetMapping("/users/rooms")
	public List<RoomUserDto> getUserRooms(HttpServletRequest request) {
		int userId = jsonWebTokenService.validateUserJWT(request);
		User user = usersService.getUserById(userId);

		if (user == null) {
			throw new RuntimeException("User id not found -" + userId);
		}
		List<RoomUserDto> rooms = new ArrayList<>();
		List<RoomUser> roomsUsers = user.getRoomsUsers();
		for (RoomUser roomUser : roomsUsers) {
			if (roomUser.getState().equals(RoomUser.State.NORMAL)) {
				RoomUserDto room = new RoomUserDto(roomUser.getRoom().getId(), roomUser.getRoom().getName(),
						roomUser.getRoom().getDescription(), roomUser.getRoom().getCoverpage());
				room.setIsFavorite(roomUser.getIsFavorite());
				room.setDate(roomUser.getRoom().getDate());
				if (roomUser.getIsAdmin()) {
					room.setOwner(roomUser.getRoom().getOwner().getId());
				}
				List<UserRoomDto> users = new ArrayList<UserRoomDto>();
				Room roomOne = roomUser.getRoom();
				List<RoomUser> userRooms = roomOne.getRoomUsers();
				for (RoomUser usr : userRooms) {
					UserRoomDto userDto = new UserRoomDto(usr.getUser().getId(), usr.getUser().getName(),
							usr.getUser().getLastname(), usr.getUser().getUsername(), usr.getUser().getEmail(),
							usr.getUser().getAvatar());
					users.add(userDto);
				}
				room.setLastUpdate(roomOne.getLastUpdate());
				room.setUsers(users);
				rooms.add(room);
			}
		}

		// Collections.reverse(rooms);

		return rooms;
	}

	@GetMapping("/users/rooms/archived")
	public List<RoomUserDto> getUserRoomsArchived(HttpServletRequest request) {
		int userId = jsonWebTokenService.validateUserJWT(request);
		User user = usersService.getUserById(userId);

		if (user == null) {
			throw new RuntimeException("User id not found -" + userId);
		}
		List<RoomUserDto> rooms = new ArrayList<>();
		List<RoomUser> roomsUsers = user.getRoomsUsers();
		for (RoomUser roomUser : roomsUsers) {
			if (roomUser.getState().equals(RoomUser.State.ARCHIVED)) {
				RoomUserDto room = new RoomUserDto(roomUser.getRoom().getId(), roomUser.getRoom().getName(),
						roomUser.getRoom().getDescription(), roomUser.getRoom().getCoverpage());
				room.setIsFavorite(roomUser.getIsFavorite());
				room.setDate(roomUser.getRoom().getDate());
				if (roomUser.getIsAdmin()) {
					room.setOwner(roomUser.getRoom().getOwner().getId());
				}
				List<UserRoomDto> users = new ArrayList<UserRoomDto>();
				Room roomOne = roomUser.getRoom();
				List<RoomUser> userRooms = roomOne.getRoomUsers();
				for (RoomUser usr : userRooms) {
					UserRoomDto userDto = new UserRoomDto(usr.getUser().getId(), usr.getUser().getName(),
							usr.getUser().getLastname(), usr.getUser().getUsername(), usr.getUser().getEmail(),
							usr.getUser().getAvatar());
					users.add(userDto);
				}
				room.setLastUpdate(roomOne.getLastUpdate());
				room.setUsers(users);
				rooms.add(room);
			}
		}

		// Collections.reverse(rooms);

		return rooms;
	}

	@GetMapping("/users/{userId}/expenses")
	public List<Expense> getUserExpenses(@PathVariable int userId) {
		User user = usersService.getUserById(userId);

		if (user == null) {
			throw new RuntimeException("User id not found -" + userId);
		}
		List<Expense> expenses = new ArrayList<>();
		expenses.addAll(user.getExpenses());

		return expenses;
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody UserDto log) {
		UserResponse jwt = new UserResponse(0, null, null, null);
		User user = usersService.getUserByUsername(log.getUsername());
		if (user == null) {
			Response response = new Response("User dont get", "Usuario Inexistente");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			String pass = user.getPassword();
			jwt.setUsername(user.getUsername());
			if (log.getPassword().equals(pass) && user.getGoogleId() == null) {
				String token = jsonWebTokenService.generateJWTToken("" + user.getId());
				jwt.setToken(token);
				jwt.setId(user.getId());
				jwt.setAvatar(user.getAvatar());
				return new ResponseEntity<>(jwt, HttpStatus.OK);
			} else {
				Response response = new Response("Password not match", "Contraseña incorrecta");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

		}

	}

	@PostMapping("/social-login")
	public ResponseEntity<Object> socialLogin(@RequestBody SocialUserDto social) {
		try {
			GoogleUserDto googleUser = googleService.getGoogleUser(social.getToken());
			User user = usersService.getUserByGoogleId(googleUser.getId());
			if (user == null) {
				if (social.getUsername() == null) {
					Response failResponse = new Response("Error registro", "Debe registrar un nombre de usuario");
					return new ResponseEntity<>(failResponse, HttpStatus.I_AM_A_TEAPOT);
				} else {
					if (usersService.getUserByUsername(social.getUsername()) == null) {
						user = new User(googleUser.getName(), googleUser.getGivenName(), social.getUsername(),
								googleUser.getEmail(), googleUser.getId(), "goo", googleUser.getPictureUrl());
						int id = usersService.saveUser(user);
						String token = jsonWebTokenService.generateJWTToken("" + user.getId());
						UserResponse response = new UserResponse(id, user.getUsername(), token,
								googleUser.getPictureUrl());
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
						Response failResponse = new Response("Error registro",
								"El nombre de usuario ya esta registrado");
						return new ResponseEntity<>(failResponse, HttpStatus.I_AM_A_TEAPOT);
					}

				}
			} else {
				String token = jsonWebTokenService.generateJWTToken("" + user.getId());
				UserResponse response = new UserResponse(user.getId(), user.getUsername(), token,
						googleUser.getPictureUrl());

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.println(ex);
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@PostMapping("/users")
	public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
		try {
			User user = usersService.getUserByUsername(userDto.getUsername());
			if (user == null) {
				Avatar avatar = avatarsService.getAvatarById(userDto.getAvatar());
				user = new User(userDto.getName(), userDto.getLastname(), userDto.getUsername(), userDto.getEmail(),
						userDto.getPassword(), avatar.getUrl());

				int id = usersService.saveUser(user);
				String token = jsonWebTokenService.generateJWTToken("" + user.getId());
				UserResponse response = new UserResponse(id, user.getUsername(), token, avatar.getUrl());

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
				User user = usersService.getUserById(userDto.getId());
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
			User user = usersService.getUserById(userPayload);
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