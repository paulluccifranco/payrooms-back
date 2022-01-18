package com.modelDtos;

public class RoomDto {

	private String name;
	private String description;
	private int coverPage;
	private int owner;

	public RoomDto(String name, String description, int coverPage, int owner) {
		super();
		this.name = name;
		this.description = description;
		this.coverPage = coverPage;
		this.owner = owner;
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

	public int getCoverPage() {
		return coverPage;
	}

	public void setCoverPage(int coverPage) {
		this.coverPage = coverPage;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

}
