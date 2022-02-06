package com.payroom.dao;

import java.util.List;

import com.payroom.model.Payment;

public interface PaymentDAO {

	public List<Payment> findPaymentsList();

	public Payment findPaymentById(int id);

	public int savePayment(Payment payment);

	public void deletePayment(int id);

}
