package com.controller;

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

import com.model.Payment;
import com.model.Room;
import com.model.User;
import com.modelDtos.PaymentDto;
import com.modelDtos.Response;
import com.security.JsonWebTokenService;
import com.service.PaymentService;
import com.service.RoomService;
import com.service.UserService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
		RequestMethod.DELETE })
@RequestMapping("/api/v1.0")

public class PaymentRestController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Autowired
	JsonWebTokenService jsonWebTokenService;

	@GetMapping("/payments/{paymentId}")
	public Payment getPayment(@PathVariable int paymentId) {
		Payment payment = paymentService.findPaymentById(paymentId);

		if (payment == null) {
			throw new RuntimeException("Payment id not found -" + paymentId);
		}
		return payment;
	}

	@PostMapping("/payments/{roomId}/{recieverId}")
	public ResponseEntity<Object> addPayment(HttpServletRequest request, @RequestBody PaymentDto paymentDto,
			@PathVariable int roomId, @PathVariable int recieverId) {

		try {
			int payerId = jsonWebTokenService.validateUserJWT(request);
			User reciever = userService.findUserById(recieverId);
			User payer = userService.findUserById(payerId);
			Room room = roomService.findRoomById(roomId);

			if (reciever == null || payer == null) {
				return new ResponseEntity<>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
			}
			Payment payment = new Payment(room, reciever, payer, paymentDto.getDescription(), paymentDto.getValue());
			payment.setId(0);

			int id = paymentService.savePayment(payment);

			return new ResponseEntity<>("Pago Agregado, id pago = " + id, HttpStatus.OK);

		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "Los datos recibidos no son correctos");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	}

	@PutMapping("/payments")
	public Payment updatePayment(@RequestBody Payment payment) {

		paymentService.savePayment(payment);

		return payment;
	}

	@DeleteMapping("payments/{paymentId}")
	public String detetePayment(@PathVariable int paymentId) {

		Payment payment = paymentService.findPaymentById(paymentId);

		if (payment == null) {
			throw new RuntimeException("Payment id not found -" + paymentId);
		}

		paymentService.deletePayment(paymentId);

		return "Deleted payment id - " + paymentId;
	}

}