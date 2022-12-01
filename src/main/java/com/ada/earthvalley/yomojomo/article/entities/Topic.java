package com.ada.earthvalley.yomojomo.article.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ada.earthvalley.yomojomo.article.entities.enums.MajorTopicType;
import com.ada.earthvalley.yomojomo.article.entities.enums.SubTopicType;
import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;
import com.ada.earthvalley.yomojomo.user.entities.UserTopic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Topic extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "topic_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private MajorTopicType majorTopic;

	@Enumerated(EnumType.STRING)
	private SubTopicType subTopic;

	@OneToMany(mappedBy = "topic")
	private List<UserTopic> userTopics = new ArrayList<>();
}
