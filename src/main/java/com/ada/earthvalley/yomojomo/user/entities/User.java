package com.ada.earthvalley.yomojomo.user.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import com.ada.earthvalley.yomojomo.auth.entities.RefreshToken;
import com.ada.earthvalley.yomojomo.group.entities.GroupUser;
import com.ada.earthvalley.yomojomo.user.entities.enums.UserRole;
import com.ada.earthvalley.yomojomo.word.entities.Word;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private UUID id;

	private String username;

	private String nickname;

	private String phone;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Word> words = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserTopic> userTopics = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<GroupUser> groupUsers = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Embedded
	@Column(updatable = false)
	private RefreshToken refreshToken;
}
