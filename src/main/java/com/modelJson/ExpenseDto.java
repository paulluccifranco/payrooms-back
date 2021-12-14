package com.modelJson;

import java.math.BigDecimal;

public class ExpenseDto {

	private String description;
	private BigDecimal value;
	private int idUser;
	private int idRoom;

	public ExpenseDto(String description, BigDecimal value, int idUser, int idRoom) {
		super();
		this.description = description;
		this.value = value;
		this.idUser = idUser;
		this.idRoom = idRoom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
