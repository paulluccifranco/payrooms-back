package com.modelDtos;

import java.math.BigDecimal;

public class ExpenseDto {

	private int id;
	private String description;
	private BigDecimal value;
	private int idUser;
	private int idRoom;
	private String participants;

	public ExpenseDto() {
		super();
	}

	public ExpenseDto(int id, String description, BigDecimal value, int idUser, int idRoom, String participants) {
		super();
		this.id = id;
		this.description = description;
		this.value = value;
		this.idUser = idUser;
		this.idRoom = idRoom;
		this.participants = participants;
	}

	public ExpenseDto(String description, BigDecimal value, int idUser, int idRoom, String participants) {
		super();
		this.description = description;
		this.value = value;
		this.idUser = idUser;
		this.idRoom = idRoom;
		this.participants = participants;
	}

	public ExpenseDto(int id, String description, BigDecimal value) {
		super();
		this.id = id;
		this.description = description;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

}
