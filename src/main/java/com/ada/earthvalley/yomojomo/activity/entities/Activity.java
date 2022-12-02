package com.ada.earthvalley.yomojomo.activity.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ada.earthvalley.yomojomo.activity.entities.enums.ActivityStatus;
import com.ada.earthvalley.yomojomo.group.entities.Group;
import com.ada.earthvalley.yomojomo.user.entities.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Activity {
	@Id
	@GeneratedValue
	@Column(name = "activity_id")
	private Long id;

	// TODO: 지금은 ERD 로 연결
	private Long articleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@OneToMany(mappedBy = "activity")
	private List<MainSentence> mainSentences = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private ActivityStatus status;
	private String summary;
}
