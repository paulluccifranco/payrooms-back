package com.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

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
	private CoverPage coverpage;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	@JsonManagedReference
	private User owner;

	@JoinTable(name = "rooms_users", joinColumns = @JoinColumn(name = "room_fk_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "user_fk_id", nullable = false))
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<User> users;

	@OneToMany(mappedBy = "room", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonManagedReference
	private List<Payment> roomPayments;

	@OneToMany(mappedBy = "room", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonManagedReference
	private List<Expense> expenses;

	@Column(name = "room_date")
	@UpdateTimestamp
	private Date date;

	@Column(name = "room_state")
	private int state;

	public Room() {
		super();
	}

	public Room(String name, String description, CoverPage coverPage, User owner) {
		super();
		this.name = name;
		this.description = description;
		this.coverpage = coverPage;
		this.owner = owner;
		List<User> list = new ArrayList<User>();
		list.add(owner);
		this.users = list;
		this.state = 1;
	}

	public Room(String name, String description, CoverPage coverPage, User owner, List<User> users) {
		super();
		this.name = name;
		this.description = description;
		this.coverpage = coverPage;
		this.owner = owner;
		this.users = users;
		this.state = 1;
	}

	public void addUsers(User theUser) {
		if (theUser == null) {
			users = new ArrayList<>();
		}

		users.add(theUser);

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

	public CoverPage getCoverPage() {
		return coverpage;
	}

	public void setCoverPage(CoverPage coverPage) {
		this.coverpage = coverPage;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CoverPage getCoverpage() {
		return coverpage;
	}

	public void setCoverpage(CoverPage coverpage) {
		this.coverpage = coverpage;
	}

	public List<Payment> getRoomPayments() {
		return roomPayments;
	}

	public void setRoomPayments(List<Payment> roomPayments) {
		this.roomPayments = roomPayments;
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
		return "Room [id=" + id + ", name=" + name + ", description=" + description + ", coverPage=" + coverpage
				+ ", owner=" + owner + ", users=" + users + ", expenses=" + expenses + ", state=" + state + "]";
	}

}
