package com.payroom.controller;

import java.util.Date;

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

import com.payroom.dao.RoomLogDAO;
import com.payroom.model.Payment;
import com.payroom.model.Room;
import com.payroom.model.RoomLog;
import com.payroom.model.User;
import com.payroom.modelDtos.LogRoomType;
import com.payroom.modelDtos.PaymentDto;
import com.payroom.modelDtos.Response;
import com.payroom.security.JsonWebTokenService;
import com.payroom.service.PaymentService;
import com.payroom.service.RoomService;
import com.payroom.service.UserService;

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

	@Autowired
	RoomLogDAO roomLogDAO;

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
			RoomLog roomLog = new RoomLog(room, payer, new Date(), LogRoomType.PAY_ADDED, id);
			roomLogDAO.saveRoomLog(roomLog);

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

		RoomLog roomLog = new RoomLog(payment.getRoom(), payment.getPayer(), new Date(), LogRoomType.PAY_DELETED,
				payment.getId());
		roomLogDAO.saveRoomLog(roomLog);

		paymentService.deletePayment(paymentId);

		return "Deleted payment id - " + paymentId;
	}

}