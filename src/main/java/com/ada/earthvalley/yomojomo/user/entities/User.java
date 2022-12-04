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

import com.ada.earthvalley.yomojomo.activity.entities.Activity;
import com.ada.earthvalley.yomojomo.activity.entities.Reading;
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

	// TODO: User 가 삭제되어도 Reading 은 삭제되지 않게 null 생명주기 분리 및 null 값 채워넣기 (by Leo -22.12.03)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reading> readings = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Activity> activities = new ArrayList<>();

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

	public void addActivities(Activity activity) {
		this.activities.add(activity);
		activity.setUser(this);
	}


	public void addReadings(Reading reading) {
		this.readings.add(reading);
		reading.setUser(this);
	}

	public void addReadings(List<Reading> readings) {
		readings.forEach(reading -> {
			this.readings.add(reading);
			reading.setUser(this);
		});
	}
}
