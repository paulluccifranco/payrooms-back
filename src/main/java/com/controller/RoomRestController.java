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

import com.model.CoverPage;
import com.model.Room;
import com.model.User;
import com.modelDtos.Response;
import com.modelDtos.RoomDto;
import com.security.JsonWebTokenService;
import com.service.CoverPageService;
import com.service.RoomService;
import com.service.UserService;

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
	private CoverPageService coverPageService;

	@Autowired
	JsonWebTokenService jsonWebTokenService;

	@GetMapping("/rooms")
	public ResponseEntity<List<Room>> findAll() {
		return new ResponseEntity<>(roomService.findRoomsList(), HttpStatus.OK);
	}

	@GetMapping("/rooms/{roomId}")
	public ResponseEntity<Object> getRoom(@PathVariable int roomId) {
		Room room = roomService.findRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(room, HttpStatus.OK);
		}
	}

	@GetMapping("/rooms/{roomId}/users")
	public ResponseEntity<?> getRoomUsers(@PathVariable int roomId) {
		Room room = roomService.findRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(room.getUsers(), HttpStatus.OK);
		}
	}

	@GetMapping("/rooms/{roomId}/payments")
	public ResponseEntity<?> getRoomPayments(@PathVariable int roomId) {
		Room room = roomService.findRoomById(roomId);

		if (room == null) {
			Response response = new Response("Data not found", "Los datos no estan cargados en la base");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(room.getRoomPayments(), HttpStatus.OK);
		}
	}

	@GetMapping("/rooms/{roomId}/expenses")
	public ResponseEntity<?> getRoomExpenses(@PathVariable int roomId) {
		Room room = roomService.findRoomById(roomId);

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
			CoverPage coverpage = coverPageService.findCoverPageById(roomDto.getCoverPage());
			User owner = userService.findUserById(userId);

			if (owner == null || coverpage == null) {
				Response response = new Response("Data not found", "Los datos no estan cargados en la base");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			Room room = new Room(roomDto.getName(), roomDto.getDescription(), coverpage, owner);
			room.setId(0);

			roomService.saveRoom(room);

			return new ResponseEntity<>(room, HttpStatus.OK);

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	@PutMapping("/rooms/{roomId}/{userId}")
	public ResponseEntity<Object> addUser(@PathVariable int roomId, @PathVariable int userId) {
		try {
			User user = userService.findUserById(userId);
			Room room = roomService.findRoomById(roomId);
			if (user == null) {
				Response response = new Response("User not found", "El usuario no se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (room.getUsers().contains(user)) {
				Response response = new Response("Duplicated user", "El usuario ya se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				room.addUsers(user);
				roomService.saveRoom(room);
				Response response = new Response("User added", "El usuario fue agregado satisfactoriamente");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} catch (Exception ex) {
			Response response = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@PutMapping("/rooms")
	public ResponseEntity<Object> updateRoom(HttpServletRequest request, @RequestBody RoomDto roomDto) {
		try {
			int userId = jsonWebTokenService.validateUserJWT(request);
			Room room = roomService.findRoomById(roomDto.getId());
			if (userId == room.getOwner().getId()) {
				CoverPage coverPage = coverPageService.findCoverPageById(roomDto.getCoverPage());
				room.setCoverpage(coverPage);
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

	@PutMapping("/rooms/favorite/{roomId}")
	public ResponseEntity<Response> addRoomToFavorite(HttpServletRequest request, @PathVariable int roomId) {
		try {
			int userId = jsonWebTokenService.validateUserJWT(request);
			Room room = roomService.findRoomById(roomId);
			User user = userService.findUserById(userId);
			if (room.getUsers().remove(user)) {
				room.addFavoriteUser(user);
				roomService.saveRoom(room);
			} else {
				Response response = new Response("Room not found", "La sala no fue encontrada");
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}

			Response response = new Response("Room added", "La sala se agrego a favoritos");
			return new ResponseEntity<Response>(response, HttpStatus.OK);

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}
	}

	@DeleteMapping("rooms/{roomId}")
	public ResponseEntity<Object> deteteRoom(HttpServletRequest request, @PathVariable int roomId) {

		try {
			Room room = roomService.findRoomById(roomId);

			if (room == null) {
				Response response = new Response("Room not found", "La sala no se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				int userId = jsonWebTokenService.validateUserJWT(request);
				if (userId == room.getOwner().getId()) {
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

}