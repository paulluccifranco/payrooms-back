package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.PaymentDAO;
import com.model.Payment;
import com.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDAO paymentDAO;

	@Override
	public List<Payment> findPaymentsList() {
		List<Payment> listUsers = paymentDAO.findPaymentsList();
		return listUsers;
	}

	@Override
	public Payment findPaymentById(int id) {
		Payment user = paymentDAO.findPaymentById(id);
		return user;
	}

	@Override
	public int savePayment(Payment payment) {
		paymentDAO.savePayment(payment);
		return payment.getId();

	}

	@Override
	public void deletePayment(int id) {
		paymentDAO.deletePayment(id);
	}

}
