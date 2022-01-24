package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Column(name = "user_username", length = 45, nullable = false, unique = true)
	private String username;

	@Column(name = "user_email", length = 45, nullable = false)
	private String email;

	@Column(name = "user_password", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "avatar_id")
	private Avatar avatar;

	@OneToMany(mappedBy = "owner", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Room> ownRooms;

	@ManyToMany(mappedBy = "users")
	@JsonIgnore
	private Set<Room> rooms;

	@ManyToMany(mappedBy = "favoriteUsers")
	@JsonIgnore
	private List<Room> favoriteRooms;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<RoomLog> roomLogs;

	@ManyToMany(mappedBy = "friends")
	@JsonIgnore
	private Set<User> users;

	@JoinTable(name = "users_friends", joinColumns = @JoinColumn(name = "user_fk_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "friend_fk_id", nullable = false))
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<User> friends;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Expense> expenses;

	@Column(name = "user_state")
	private int state = 1;

	@Column(name = "user_date")
	@UpdateTimestamp
	private Date date;

	@OneToMany(mappedBy = "reciever", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Payment> paymentRecieved;

	@OneToMany(mappedBy = "payer", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Payment> paymentPayed;

	public User() {
		super();
	}

	public User(int id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public User(String name, String lastname, String username, String email, String password, Avatar avatar) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public void addRoom(Room theRoom) {
		if (ownRooms == null) {
			ownRooms = new ArrayList<>();
		}

		ownRooms.add(theRoom);

		theRoom.setOwner(this);
	}

	public List<Room> getFavoriteRooms() {
		return favoriteRooms;
	}

	public void setFavoriteRooms(List<Room> favoriteRooms) {
		this.favoriteRooms = favoriteRooms;
	}

	public List<RoomLog> getRoomLogs() {
		return roomLogs;
	}

	public void setRoomLogs(List<RoomLog> roomLogs) {
		this.roomLogs = roomLogs;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public void addFriend(User friend) {
		if (friend == null) {
			friends = new HashSet<>();
		}

		friends.add(friend);

	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public void addExpense(Expense theExpense) {
		if (expenses == null) {
			expenses = new ArrayList<>();
		}

		expenses.add(theExpense);
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

	public List<Payment> getPaymentRecieved() {
		return paymentRecieved;
	}

	public void setPaymentRecieved(List<Payment> paymentRecieved) {
		this.paymentRecieved = paymentRecieved;
	}

	public List<Payment> getPaymentPayed() {
		return paymentPayed;
	}

	public void setPaymentPayed(List<Payment> paymentPayed) {
		this.paymentPayed = paymentPayed;
	}

	public void addPayed(Payment payed) {
		if (paymentPayed == null) {
			paymentPayed = new ArrayList<>();
		}

		paymentPayed.add(payed);
	}

	public void addRecieved(Payment recieved) {
		if (paymentRecieved == null) {
			paymentRecieved = new ArrayList<>();
		}

		paymentRecieved.add(recieved);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username + ", password="
				+ password + ", avatar=" + avatar.getId() + ", state=" + state + "]";
	}

}
