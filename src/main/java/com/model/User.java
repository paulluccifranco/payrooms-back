package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;

	@Column(name = "user_name", length = 45)
	private String name;

	@Column(name = "user_lastname", length = 45)
	private String lastname;

	@Column(name = "user_username", length = 45, nullable = false)
	private String username;

	@Column(name = "user_password", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "avatar_id")
	private Avatar avatar;

	@OneToMany(mappedBy = "owner", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonBackReference
	private List<Room> ownRooms;

	@ManyToMany(mappedBy = "users")
	@JsonBackReference
	private List<Room> rooms;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonBackReference
	private List<Expense> expenses;

	@Column(name = "user_state")
	private int state = 1;

	public User() {
		super();
	}

	public User(int id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public User(String name, String lastname, String username, String password, Avatar avatar) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.avatar = avatar;
	}

	public void addRoom(Room theRoom) {
		if (ownRooms == null) {
			ownRooms = new ArrayList<>();
		}

		ownRooms.add(theRoom);

		theRoom.setOwner(this);
	}

	public void addExpense(Expense theExpense) {
		if (expenses == null) {
			expenses = new ArrayList<>();
		}

		expenses.add(theExpense);
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public List<Room> getOwnRooms() {
		return ownRooms;
	}

	public void setOwnRooms(List<Room> ownRooms) {
		this.ownRooms = ownRooms;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username + ", password="
				+ password + ", avatar=" + avatar.getId() + ", state=" + state + "]";
	}

}
