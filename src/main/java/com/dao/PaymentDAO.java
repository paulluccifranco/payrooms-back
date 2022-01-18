package com.dao;

import java.util.List;

import com.model.Payment;

public interface PaymentDAO {

	public List<Payment> findPaymentsList();

	public Payment findPaymentById(int id);

	public int savePayment(Payment payment);

	public void deletePayment(int id);

}
