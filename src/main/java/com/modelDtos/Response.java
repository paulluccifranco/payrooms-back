package com.modelDtos;

public class Response {

	private String messaje;

	private Object response;

	public Response(String messaje, Object response) {
		super();
		this.messaje = messaje;
		this.response = response;
	}

	public String getMessaje() {
		return messaje;
	}

	public void setMessaje(String messaje) {
		this.messaje = messaje;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
