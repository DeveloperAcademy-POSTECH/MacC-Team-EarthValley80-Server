package com.ada.earthvalley.yomojomo.user.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;
import com.ada.earthvalley.yomojomo.auth.entities.RefreshToken;
import com.ada.earthvalley.yomojomo.group.entities.GroupUser;
import com.ada.earthvalley.yomojomo.user.entities.enums.UserRole;
import com.ada.earthvalley.yomojomo.word.entities.Word;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", columnDefinition = "uuid")
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

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "token_id")
	private RefreshToken refreshToken;

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = new RefreshToken(refreshToken);
	}

	public String getIdString() {
		return this.id.toString();
	}

	@Builder
	public User(String username, String nickname) {
		this.username = username;
		this.nickname = nickname;
	}
}
