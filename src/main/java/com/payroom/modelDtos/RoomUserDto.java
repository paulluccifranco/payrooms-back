package com.payroom.modelDtos;

import java.util.Date;
import java.util.List;

import com.payroom.model.Coverpage;
import com.payroom.model.Expense;

public class RoomUserDto {

	private int id;
	private String name;
	private String description;
	private Coverpage coverpage;
	private int owner;
	private Boolean state;
	private Boolean isFavorite;
	private Boolean isAdmin;
	private List<UserRoomDto> users;
	private Date date;
	private List<Expense> expenses;
	private Boolean active;
	private Date lastUpdate;

	public RoomUserDto() {
		super();
	}

	public RoomUserDto(int id, String name, String description, Coverpage coverpage, int owner) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coverpage = coverpage;
		this.owner = owner;
	}

	public RoomUserDto(String name, String description, Coverpage coverpage, int owner) {
		super();
		this.name = name;
		this.description = description;
		this.coverpage = coverpage;
		this.owner = owner;
	}

	public RoomUserDto(int id, String name, String description, Coverpage coverpage) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coverpage = coverpage;
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

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<UserRoomDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserRoomDto> users) {
		this.users = users;
	}

	public Date getDate() {
		return date;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public void setActive(Boolean active) {
		this.active = active;
	}

}
