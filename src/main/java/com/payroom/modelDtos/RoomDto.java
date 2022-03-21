package com.payroom.modelDtos;

public class RoomDto {

	private int id;
	private String name;
	private String description;
	private int coverpage;
	private int owner;

	public RoomDto() {
		super();
	}

	public RoomDto(int id, String name, String description, int coverpage, int owner) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coverpage = coverpage;
		this.owner = owner;
	}

	public RoomDto(String name, String description, int coverpage, int owner) {
		super();
		this.name = name;
		this.description = description;
		this.coverpage = coverpage;
		this.owner = owner;
	}

	public RoomDto(int id, String name, String description, int coverpage) {
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

	public int getCoverpage() {
		return coverpage;
	}

	public void setCoverpage(int coverpage) {
		this.coverpage = coverpage;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

}
