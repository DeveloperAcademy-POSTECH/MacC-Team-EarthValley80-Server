package com.ada.earthvalley.yomojomo.group.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;
import com.ada.earthvalley.yomojomo.group.entities.enums.GroupType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "groups")
public class Group extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_id")
	private long id;

	private String title;

	// TODO: Group 이 삭제되었을 때 NIE 와 긴밀한 관계가 있는 GroupUser 는 어떻게 되야할까.
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GroupUser> groupUsers = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private GroupType type;

	@OneToOne(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private Invitation invitation;
}
