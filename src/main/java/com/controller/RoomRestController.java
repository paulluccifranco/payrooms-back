package com.controller;

import java.util.List;

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
import com.model.Expense;
import com.model.Room;
import com.model.User;
import com.modelJson.RoomDto;
import com.modelJson.UserResponse;
import com.service.CoverPageService;
import com.service.RoomService;
import com.service.UserService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST })
@RequestMapping("/api/v1.0")

public class RoomRestController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Autowired
	private CoverPageService coverPageService;

	@GetMapping("/rooms")
	public List<Room> findAll() {
		return roomService.findRoomsList();
	}

	@GetMapping("/rooms/{roomId}")
	public Room getRoom(@PathVariable int roomId) {
		Room room = roomService.findRoomById(roomId);

		if (room == null) {
			throw new RuntimeException("Room id not found -" + roomId);
		}
		return room;
	}

	@GetMapping("/rooms/{roomId}/users")
	public List<User> getRoomUsers(@PathVariable int roomId) {
		Room room = roomService.findRoomById(roomId);

		if (room == null) {
			throw new RuntimeException("Room id not found -" + roomId);
		}
		return room.getUsers();
	}

	@GetMapping("/rooms/{roomId}/expenses")
	public List<Expense> getRoomExpenses(@PathVariable int roomId) {
		Room room = roomService.findRoomById(roomId);

		if (room == null) {
			throw new RuntimeException("Room id not found -" + roomId);
		}
		return room.getExpenses();
	}

	@PostMapping("/rooms")
	public UserResponse addRoom(@RequestBody RoomDto roomDto) {
		UserResponse response = new UserResponse(0, "Problemas al crear la sala", null);
		CoverPage coverpage = coverPageService.findCoverPageById(roomDto.getCoverPage());
		User owner = userService.findUserById(roomDto.getOwner());

		if (owner == null || coverpage == null) {
			return response;
		}
		Room room = new Room(roomDto.getName(), roomDto.getDescription(), coverpage, owner);
		room.setId(0);

		int id = roomService.saveRoom(room);

		if (id > 0) {
			response.setId(id);
			response.setUsername("Sala creada");
		}

		return response;

	}

	@PutMapping("/rooms/{roomId}/{userId}")
	public ResponseEntity<String> addUser(@PathVariable int roomId, @PathVariable int userId) {
		User user = userService.findUserById(userId);
		Room room = roomService.findRoomById(roomId);
		String response = "Usuario Agregado";
		HttpStatus status = HttpStatus.OK;
		try {
			List<User> users = room.getUsers();
			for (User usr : users) {
				if (usr == user) {
					response = "El usuario ya se encuentra en la sala";
					status = HttpStatus.BAD_REQUEST;
				} else {
					room.addUsers(user);
					roomService.saveRoom(room);
				}
			}
		} catch (Exception ex) {
			room.addUsers(user);
			roomService.saveRoom(room);
		}

		return new ResponseEntity<String>(response, status);

	}

	@PutMapping("/rooms")
	public Room updateRoom(@RequestBody Room room) {

		roomService.saveRoom(room);

		return room;
	}

	@DeleteMapping("rooms/{roomId}")
	public String deteteRoom(@PathVariable int roomId) {

		Room room = roomService.findRoomById(roomId);

		if (room == null) {
			throw new RuntimeException("Room id not found -" + roomId);
		}

		roomService.deleteRoom(roomId);

		return "Deleted room id - " + roomId;
	}

}