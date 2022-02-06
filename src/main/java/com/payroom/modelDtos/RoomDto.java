package com.payroom.modelDtos;

public class RoomDto {

	private int id;
	private String name;
	private String description;
	private int coverPage;
	private int owner;

	public RoomDto() {
		super();
	}

	public RoomDto(int id, String name, String description, int coverPage, int owner) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coverPage = coverPage;
		this.owner = owner;
	}

	public RoomDto(String name, String description, int coverPage, int owner) {
		super();
		this.name = name;
		this.description = description;
		this.coverPage = coverPage;
		this.owner = owner;
	}

	public RoomDto(int id, String name, String description, int coverPage) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coverPage = coverPage;
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
