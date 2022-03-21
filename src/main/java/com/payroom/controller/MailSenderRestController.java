package com.payroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payroom.model.User;
import com.payroom.service.UserService;
import com.payroom.util.Email;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/api/v1.0")
public class MailSenderRestController {

	@Autowired
	private Email emailService;

	@Autowired
	private UserService userService;

	@GetMapping("/sendemail/hola")
	public String hola() {
		return "hola";
	}

	@PostMapping("/sendemail/{userId}")
	public String sendEmail(@PathVariable int userId) {

		User user = userService.getUserById(userId);
		String email = user.getEmail();

		emailService.enviarMensaje("noreply@gmail.com", email, "Registro en nueva sala",
				"Acceda al siguiente link para usar la sala");

		return "hola";

	}

}
