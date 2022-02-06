package com.payroom.modelDtos;

import java.util.Date;

public class Response {

	private String developerMessage;

	private String userMessage;

	private Date time;

	public Response(String developerMessage, String userMessage) {
		super();
		this.developerMessage = developerMessage;
		this.userMessage = userMessage;
		this.time = new Date();
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
