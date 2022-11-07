package com.ada.earthvalley.yomojomo.nie.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;
import com.ada.earthvalley.yomojomo.group.entities.GroupUser;
import com.ada.earthvalley.yomojomo.nie.entities.enums.NieStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: GroupUser 가 orphan 이 되어서 삭제된다면 NIE 는 어떻게 되는게 맞는가 ?

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Nie extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nie_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_user_id")
	private GroupUser groupUser;

	@OneToMany(mappedBy = "nie", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<NieProcess> processes = new ArrayList<>();

	@Embedded
	@Column(updatable = false)
	private NieContext context;

	@Enumerated(EnumType.STRING)
	private NieStatus status;

	private long articleId;
}
