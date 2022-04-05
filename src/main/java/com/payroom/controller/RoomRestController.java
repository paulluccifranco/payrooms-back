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

import com.payroom.model.Coverpage;
import com.payroom.model.Room;
import com.payroom.model.RoomUser;
import com.payroom.model.User;
import com.payroom.modelDtos.Response;
import com.payroom.modelDtos.RoomDto;
import com.payroom.modelDtos.RoomUserDto;
import com.payroom.modelDtos.UserRoomDto;
import com.payroom.security.JsonWebTokenService;
import com.payroom.service.CoverpageService;
import com.payroom.service.RoomService;
import com.payroom.service.RoomUserService;
import com.payroom.service.UserService;
import com.payroom.util.Email;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
		RequestMethod.DELETE })
@RequestMapping("/api/v1.0")

public class RoomRestController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Autowired
	private CoverpageService coverpageService;

	@Autowired
	private RoomUserService roomUserService;

	@Autowired
	private JsonWebTokenService jsonWebTokenService;

	@Autowired
	private Email emailService;

	@GetMapping("/rooms")
	public ResponseEntity<List<Room>> getAll() {
		return new ResponseEntity<>(roomService.getRoomsList(), HttpStatus.OK);
	}

	@GetMapping("/rooms/{roomId}")
	public ResponseEntity<Object> getRoom(@PathVariable int roomId) {
		Room room = roomService.getRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			List<RoomUser> roomsUsers = room.getRoomUsers();
			RoomUserDto roomUserDto = new RoomUserDto(room.getId(), room.getName(), room.getDescription(),
					room.getCoverpage());
			roomUserDto.setDate(room.getDate());
			List<UserRoomDto> users = new ArrayList<UserRoomDto>();
			for (RoomUser userRoom : roomsUsers) {
				UserRoomDto userDto = new UserRoomDto(userRoom.getUser().getId(), userRoom.getUser().getName(),
						userRoom.getUser().getLastname(), userRoom.getUser().getUsername(),
						userRoom.getUser().getEmail(), userRoom.getUser().getAvatar());
				userDto.setAdmin(userRoom.getIsAdmin());
				userDto.setActive(userRoom.getState());
				users.add(userDto);
			}
			roomUserDto.setActive(true);
			roomUserDto.setOwner(room.getOwner().getId());
			roomUserDto.setExpenses(room.getExpenses());
			roomUserDto.setUsers(users);
			return new ResponseEntity<>(roomUserDto, HttpStatus.OK);
		}
	}

	@GetMapping("/sharedroom/{roomJWT}")
	public ResponseEntity<Object> getRoomDecoded(@PathVariable String roomJWT) {
		int roomId = jsonWebTokenService.validateRoomJWT(roomJWT);

		Room room = roomService.getRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			List<RoomUser> roomsUsers = room.getRoomUsers();
			RoomUserDto roomUserDto = new RoomUserDto(room.getId(), room.getName(), room.getDescription(),
					room.getCoverpage());
			roomUserDto.setDate(room.getDate());
			List<UserRoomDto> users = new ArrayList<UserRoomDto>();
			for (RoomUser userRoom : roomsUsers) {
				UserRoomDto userDto = new UserRoomDto(userRoom.getUser().getId(), userRoom.getUser().getName(),
						userRoom.getUser().getLastname(), userRoom.getUser().getUsername(),
						userRoom.getUser().getEmail(), userRoom.getUser().getAvatar());
				userDto.setAdmin(userRoom.getIsAdmin());
				userDto.setActive(userRoom.getState());
				users.add(userDto);
			}
			roomUserDto.setActive(true);
			roomUserDto.setOwner(room.getOwner().getId());
			roomUserDto.setExpenses(room.getExpenses());
			roomUserDto.setUsers(users);
			return new ResponseEntity<>(roomUserDto, HttpStatus.OK);
		}
	}

	@GetMapping("/rooms/sharedlink/{roomId}")
	public ResponseEntity<Object> getRoomJWT(@PathVariable int roomId) {
		Room room = roomService.getRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			String token = jsonWebTokenService.generateRoomJWT("" + roomId);
			return new ResponseEntity<>(token, HttpStatus.OK);
		}
	}

	@GetMapping("/rooms/{roomId}/users")
	public ResponseEntity<?> getRoomUsers(@PathVariable int roomId) {
		Room room = roomService.getRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			List<User> users = new ArrayList<User>();
			List<RoomUser> roomsUsers = room.getRoomUsers();
			for (RoomUser roomUser : roomsUsers) {
				users.add(roomUser.getUser());
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
	}

	@GetMapping("/rooms/{roomId}/payments")
	public ResponseEntity<?> getRoomPayments(@PathVariable int roomId) {
		Room room = roomService.getRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(room.getRoomPayments(), HttpStatus.OK);
		}
	}

	@GetMapping("/rooms/{roomId}/expenses")
	public ResponseEntity<?> getRoomExpenses(@PathVariable int roomId) {
		Room room = roomService.getRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(room.getExpenses(), HttpStatus.OK);
		}

	}

	@PostMapping("/rooms")
	public ResponseEntity<Object> addRoom(HttpServletRequest request, @RequestBody RoomDto roomDto) {
		try {
			int userId = jsonWebTokenService.validateUserJWT(request);
			Coverpage coverpage = coverpageService.getCoverpageById(roomDto.getCoverpage());
			User owner = userService.getUserById(userId);

			if (owner == null || coverpage == null) {
				Response response = new Response("Data not found", "Los datos no estan cargados en la base");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			Room room = new Room(roomDto.getName(), roomDto.getDescription(), coverpage, owner);
			room.setId(0);

			roomService.saveRoom(room);

			RoomUser roomUser = new RoomUser(room, owner, true, false, true);
			roomUser.setId(0);
			roomUserService.saveRoomUser(roomUser);

			return new ResponseEntity<>(room, HttpStatus.OK);

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	@PostMapping("/rooms/{roomId}/{userId}")
	public ResponseEntity<Object> addUser(@PathVariable int roomId, @PathVariable int userId) {
		try {
			User user = userService.getUserById(userId);
			Room room = roomService.getRoomById(roomId);
			if (user == null) {
				Response response = new Response("User not found", "El usuario no se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			RoomUser roomUser = roomUserService.getRoomUserByUserRoom(user, room);
			if (roomUser != null) {
				Response response = new Response("Duplicated user", "El usuario ya se encuentra en esta sala");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				roomUser = new RoomUser(room, user, true, false, false);
				roomUser.setId(0);
				roomUserService.saveRoomUser(roomUser);
				try {
					emailService.enviarMensajeHtml("noreply@gmail.com", user.getEmail(), "Registro en nueva sala",
							"Ha sido agregado a una nueva sala. Acceda al siguiente link <a href='https://payments-rooms-app.web.app/room/"
									+ room.getId() + "'>" + "Enlace Sala</a>");
				} catch (Exception ex) {
					System.out.println("No se envio el mensaje");
				}

				Response response = new Response("User added", "El usuario fue agregado satisfactoriamente");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} catch (Exception ex) {
			Response response = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@PostMapping("/rooms/favorite/{roomId}")
	public ResponseEntity<Response> addRoomToFavorite(HttpServletRequest request, @PathVariable int roomId) {
		try {
			int userId = jsonWebTokenService.validateUserJWT(request);
			Room room = roomService.getRoomById(roomId);
			User user = userService.getUserById(userId);
			RoomUser roomUser = roomUserService.getRoomUserByUserRoom(user, room);
			if (roomUser == null) {
				Response response = new Response("Room not found", "La sala no fue encontrada");
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
			roomUser.setIsFavorite(!roomUser.getIsFavorite());
			roomUserService.saveRoomUser(roomUser);

			Response response = new Response("Room added", "La sala se agrego a favoritos");
			return new ResponseEntity<Response>(response, HttpStatus.OK);

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}
	}

	@PutMapping("/rooms")
	public ResponseEntity<Object> updateRoom(HttpServletRequest request, @RequestBody RoomDto roomDto) {
		try {
			int userId = jsonWebTokenService.validateUserJWT(request);
			User user = userService.getUserById(userId);
			Room room = roomService.getRoomById(roomDto.getId());
			RoomUser roomUser = roomUserService.getRoomUserByUserRoom(user, room);
			if (roomUser.getIsAdmin()) {
				Coverpage coverpage = coverpageService.getCoverpageById(roomDto.getCoverpage());
				room.setCoverpage(coverpage);
				room.setDescription(roomDto.getDescription());
				room.setName(roomDto.getName());
				roomService.saveRoom(room);
				return new ResponseEntity<>(room, HttpStatus.OK);
			} else {
				Response response = new Response("User Id mismatch", "No tiene autorizacion para realizar esta accion");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	@DeleteMapping("rooms/{roomId}")
	public ResponseEntity<Object> deteteRoom(HttpServletRequest request, @PathVariable int roomId) {

		try {
			Room room = roomService.getRoomById(roomId);

			if (room == null) {
				Response response = new Response("Room not found", "La sala no se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				int userId = jsonWebTokenService.validateUserJWT(request);
				User user = userService.getUserById(userId);
				RoomUser roomUser = roomUserService.getRoomUserByUserRoom(user, room);
				if (roomUser.getIsAdmin()) {
					room.setActive(false);
					room.setDate(new Date());

					roomService.saveRoom(room);

					Response response = new Response("Room state change", "La sala fue dada de baja con exito");
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

	@DeleteMapping("rooms/{roomId}/user")
	public ResponseEntity<Object> deteteUserFromRoom(HttpServletRequest request, @PathVariable int roomId) {

		try {
			Room room = roomService.getRoomById(roomId);

			if (room == null) {
				Response response = new Response("Room not found", "La sala no se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				int userId = jsonWebTokenService.validateUserJWT(request);
				User user = userService.getUserById(userId);
				RoomUser roomUser = roomUserService.getRoomUserByUserRoom(user, room);
				if (roomUser != null) {
					roomUser.setState(false);
					roomUserService.saveRoomUser(roomUser);

					Response response = new Response("User deleted from room", "El usuario ya no pertenece a la sala");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					Response response = new Response("User not found", "El usuario no pertencece a la sala");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}

			}

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}
	}

}