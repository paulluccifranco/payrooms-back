package com.payroom.modelDtos;

public class GoogleUserDto {
	private String id;
	private String email;
	private Boolean emailVerified;
	private String name;
	private String pictureUrl;
	private String locale;
	private String familyName;
	private String givenName;

	public GoogleUserDto(String id, String email, Boolean emailVerified, String name, String pictureUrl, String locale,
			String familyName, String givenName) {
		super();
		this.id = id;
		this.email = email;
		this.emailVerified = emailVerified;
		this.name = name;
		this.pictureUrl = pictureUrl;
		this.locale = locale;
		this.familyName = familyName;
		this.givenName = givenName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

}
