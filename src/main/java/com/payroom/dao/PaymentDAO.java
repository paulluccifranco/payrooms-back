package com.payroom.dao;

import java.util.List;

import com.payroom.model.Payment;

public interface PaymentDAO {

	public List<Payment> getPaymentsList();

	public Payment getPaymentById(int id);

	public int savePayment(Payment payment);

	public void deletePayment(int id);

}
