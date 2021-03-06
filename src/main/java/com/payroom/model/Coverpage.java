package com.payroom.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "coverpages")
public class Coverpage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coverpage_id")
	private int id;

	@Column(name = "coverpage_url", length = 1024)
	private String url;

	@OneToMany(mappedBy = "coverpage", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Room> rooms;

	@Column(name = "coverpage_stock")
	@JsonIgnore
	private Boolean isStock;

	public Coverpage() {
		super();
	}

	public Coverpage(String url) {
		super();
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Boolean getIsStock() {
		return isStock;
	}

	public void setIsStock(Boolean isStock) {
		this.isStock = isStock;
	}

	@Override
	public String toString() {
		return "Coverpage [id=" + id + ", url=" + url + ", rooms=" + rooms + "]";
	}

}
