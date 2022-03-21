package com.payroom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "avatars")
public class Avatar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "avatar_id")
	private int id;

	@Column(name = "avatar_url", length = 1024)
	private String url;

	public Avatar() {
		super();
	}

	public Avatar(String url) {
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

	@Override
	public String toString() {
		return "Avatar [id=" + id + ", url=" + url + "]";
	}

}
