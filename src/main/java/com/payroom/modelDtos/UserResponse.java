package com.payroom.modelDtos;

public class UserResponse {

	private int id;
	private String username;
	private String token;
	private String avatar;

	public UserResponse() {
		super();
	}

	public UserResponse(int id, String username, String token, String avatar) {
		super();
		this.id = id;
		this.username = username;
		this.token = token;
		this.avatar = avatar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "JwtResponse [username=" + username + ", token=" + token + "]";
	}

}
