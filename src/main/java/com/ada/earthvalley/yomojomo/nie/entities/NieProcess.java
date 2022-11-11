package com.ada.earthvalley.yomojomo.nie.entities;

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

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;
import com.ada.earthvalley.yomojomo.nie.entities.enums.NieProcessType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: AllArgs 생성자 리팩터링 (by Leo - 22.11.07)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NieProcess extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nie_id")
	private Nie nie;

	@Enumerated(EnumType.STRING)
	private NieProcessType type;

	private String content;
}
