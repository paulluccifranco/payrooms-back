package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.PaymentDAO;
import com.payroom.model.Payment;
import com.payroom.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDAO paymentDAO;

	@Override
	public List<Payment> getPaymentsList() {
		List<Payment> listUsers = paymentDAO.getPaymentsList();
		return listUsers;
	}

	@Override
	public Payment getPaymentById(int id) {
		Payment user = paymentDAO.getPaymentById(id);
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
