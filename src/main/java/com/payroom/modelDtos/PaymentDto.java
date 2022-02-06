package com.payroom.modelDtos;

import java.math.BigDecimal;

public class PaymentDto {

	private String description;
	private BigDecimal value;

	public PaymentDto(String description, BigDecimal value) {
		super();
		this.description = description;
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
