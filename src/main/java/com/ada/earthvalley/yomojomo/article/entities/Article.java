package com.ada.earthvalley.yomojomo.article.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "article_id")
	private Long id;

	@NotNull
	private Long topicId;

	@NotNull
	private String initialId;

	@NotNull
	private String imageUrl;

	@NotNull
	private String title;

	@NotNull
	private String author;

	@NotNull
	private String source;

	@NotNull
	private String majorTopic;

	@OneToMany(mappedBy = "article")
	private List<Section> sectionList = new ArrayList<>();

	@Builder
	public Article(Long topicId, String initialId, String title, String author, String source,
		String majorTopic, String imageUrl) {
		this.topicId = topicId;
		this.initialId = initialId;
		this.title = title;
		this.author = author;
		this.source = source;
		this.majorTopic = majorTopic;
		this.imageUrl = imageUrl;
	}
}
