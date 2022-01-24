package com.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "expenses")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "expense_id")
	private int id;

	@Column(name = "expense_description", length = 45)
	private String description;

	@Column(name = "expense_value", nullable = false)
	private BigDecimal value;

	@Column(name = "expense_date")
	// @UpdateTimestamp
	private Date date;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "room_id")
	@JsonBackReference
	private Room room;

	@Column(name = "expense_participants", length = 45)
	private String participants;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "category_id")
	@JsonManagedReference
	private Category category;

	public Expense() {
		super();
	}

	public Expense(String description, BigDecimal value, User user, Room room, String participants) {
		super();
		this.description = description;
		this.value = value;
		this.user = user;
		this.room = room;
		this.date = new Date();
		this.participants = participants;
	}

	public Expense(String description, BigDecimal value, User user, Room room, String participants, Date date,
			Category category) {
		super();
		this.description = description;
		this.value = value;
		this.user = user;
		this.room = room;
		this.participants = participants;
		this.date = date;
		this.category = category;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public int getUserId() {
		return this.getUser().getId();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", description=" + description + ", value=" + value + ", date=" + date + ", user="
				+ user + ", room=" + room + "]";
	}

}