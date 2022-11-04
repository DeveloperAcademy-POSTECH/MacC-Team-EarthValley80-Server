package com.ada.earthvalley.yomojomo.group.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Invitation extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invitation_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	private String code;

	private LocalDateTime expiredAt;
}
