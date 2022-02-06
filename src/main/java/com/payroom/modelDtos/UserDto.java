package com.payroom.modelDtos;

public class UserDto {

	private int id;

	private String name;

	private String lastname;

	private String username;

	private String email;

	private String password;

	private Integer avatar;

	public UserDto() {
		super();
	}

	public UserDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserDto(String name, String lastname, String username, String email, String password, Integer avatar) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
	}

	public UserDto(int id, String name, String lastname, String username, String password, Integer avatar) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAvatar() {
		return avatar;
	}

	public void setAvatar(Integer avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserJson [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", avatar=" + avatar + "]";
	}

}
