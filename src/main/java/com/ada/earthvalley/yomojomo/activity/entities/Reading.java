package com.ada.earthvalley.yomojomo.activity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ada.earthvalley.yomojomo.activity.entities.enums.ReadingStatus;
import com.ada.earthvalley.yomojomo.group.entities.Group;
import com.ada.earthvalley.yomojomo.user.entities.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reading {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reading_id")
	private Long id;

	private Long articleId;

	// TODO: 생성자 정의 후 setter 제거 (by Leo - 22.12.02)
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@Enumerated(EnumType.STRING)
	private ReadingStatus status;
}
