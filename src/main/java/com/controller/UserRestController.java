package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
import com.modelDtos.UserDto;
import com.modelDtos.UserResponse;
import com.service.AvatarService;
import com.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/api/v1.0")

public class UserRestController {

	@Autowired
	private UserService usersService;

	@Autowired
	private AvatarService avatarsService;

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

		return "Servidor 2";
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
		rooms.addAll(user.getRooms());

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
	public UserResponse login(@RequestBody UserDto log) {
		Boolean exist = true;
		UserResponse jwt = new UserResponse(0, null, null);
		User user = usersService.findUserByUsername(log.getUsername());
		if (user == null) {
			jwt.setUsername("Usuario Inexistente");
			exist = false;
		}

		if (exist) {
			String pass = user.getPassword();
			jwt.setUsername(user.getUsername());
			if (log.getPassword().equals(pass)) {
				String token = getJWTToken("" + user.getId());
				jwt.setToken(token);
				jwt.setId(user.getId());
			} else {
				jwt.setUsername("Pass Incorrecto");
			}

		}

		return jwt;

	}

	@PostMapping("/users")
	public UserResponse addUser(@RequestBody UserDto userDto) {
		User user = usersService.findUserByUsername(userDto.getUsername());
		UserResponse response = new UserResponse(0, "El usuario ya existe", null);
		if (user == null) {
			Avatar avatar = avatarsService.findAvatarById(userDto.getAvatar());
			user = new User(userDto.getName(), userDto.getLastname(), userDto.getUsername(), userDto.getPassword(),
					avatar);

			int id = usersService.saveUser(user);
			String token = getJWTToken("" + user.getId());
			response.setId(id);
			response.setUsername("Ususario creado");
			response.setToken(token);
		}

		return response;
	}

	@PutMapping("/users")
	public User updateExpense(@RequestBody UserDto userDto) {

		User user = usersService.findUserById(userDto.getId());
		user.setName(userDto.getName());

		usersService.saveUser(user);

		return user;
	}

	@DeleteMapping("users/{userId}")
	public String deteteExpense(@PathVariable int userId) {

		User user = usersService.findUserById(userId);

		if (user == null) {
			throw new RuntimeException("Expense id not found -" + userId);
		}

		user.setState(2);
		user.setDate(new Date());
		usersService.saveUser(user);

		return "Deleted user id - " + userId;
	}

	private String getJWTToken(String userId) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("expensesJWT").setSubject(userId)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Token " + token;
	}

}