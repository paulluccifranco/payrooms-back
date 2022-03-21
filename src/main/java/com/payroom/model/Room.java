package com.payroom.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rooms")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int id;

	@Column(name = "room_name", length = 45, nullable = false)
	private String name;

	@Column(name = "room_description", length = 45)
	private String description;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "coverpage_id")
	private Coverpage coverpage;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "owner")
	private User owner;

	@OneToMany(mappedBy = "room")
	@JsonIgnore
	private List<RoomUser> roomUsers;

	@OneToMany(mappedBy = "room", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonManagedReference
	private List<Payment> roomPayments;

	@OneToMany(mappedBy = "room", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonManagedReference
	private List<Expense> expenses;

	@Column(name = "room_date")
	// @UpdateTimestamp
	private Date date = new Date();

	@Column(name = "room_state")
	private Boolean active;

	@OneToMany(mappedBy = "room", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<RoomLog> roomLogs;

	private Date lastUpdate = new Date();

	public Room() {
		super();
	}

	public Room(String name, String description, Coverpage coverpage, User owner) {
		super();
		this.name = name;
		this.description = description;
		this.coverpage = coverpage;
		this.active = true;
		this.owner = owner;
	}

	public void addExpenses(Expense theExpense) {
		if (theExpense == null) {
			expenses = new ArrayList<>();
		}

		expenses.add(theExpense);
		User expenseUser = theExpense.getUser();
		expenseUser.addExpense(theExpense);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Coverpage getCoverpage() {
		return coverpage;
	}

	public void setCoverpage(Coverpage coverpage) {
		this.coverpage = coverpage;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public Boolean getActive() {
		return active;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<RoomLog> getRoomLogs() {
		return roomLogs;
	}

	public List<RoomUser> getRoomUsers() {
		return roomUsers;
	}

	public void setRoomUsers(List<RoomUser> roomUsers) {
		this.roomUsers = roomUsers;
	}

	public void setRoomLogs(List<RoomLog> roomLogs) {
		this.roomLogs = roomLogs;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Payment> getRoomPayments() {
		return roomPayments;
	}

	public void setRoomPayments(List<Payment> roomPayments) {
		this.roomPayments = roomPayments;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void addPayments(Payment thePayment) {
		if (thePayment == null) {
			expenses = new ArrayList<>();
		}

		roomPayments.add(thePayment);
		User paymentPayer = thePayment.getPayer();
		User paymentReciever = thePayment.getReciever();
		paymentPayer.addPayed(thePayment);
		paymentReciever.addRecieved(thePayment);

	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", description=" + description + ", coverpage=" + coverpage
				+ ", expenses=" + expenses + ", state=" + active + "]";
	}

}
