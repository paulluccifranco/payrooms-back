package com.payroom.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.payroom.modelDtos.LogRoomType;

@Entity
@Table(name = "roomLog")
public class RoomLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roomLog_id")
	private int id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "roomLog_room")
	private Room room;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "roomLog_user")
	private User user;

	@Column(name = "roomLog_update")
	private Date update;

	@Column(name = "roomLog_type")
	@Enumerated(EnumType.STRING)
	private LogRoomType type;

	@Column(name = "roomLog_elementId")
	private int elementId;

	public RoomLog() {
		super();
	}

	public RoomLog(Room room, User user, Date update, LogRoomType type, int elementId) {
		super();
		this.room = room;
		this.user = user;
		this.update = update;
		this.type = type;
		this.elementId = elementId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public LogRoomType getType() {
		return type;
	}

	public void setType(LogRoomType type) {
		this.type = type;
	}

	public int getElementId() {
		return elementId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

}
