package com.model;

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

import com.modelDtos.LogRoomType;

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

}
