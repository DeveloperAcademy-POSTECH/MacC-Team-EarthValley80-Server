package com.ada.earthvalley.yomojomo.article.entities;

import java.sql.Struct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.ada.earthvalley.yomojomo.article.exceptions.ArticleError;
import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
	private String title;

	@NotNull
	private String author;

	@NotNull
	private String source;

	@NotNull
	private String majorTopic;

	@Builder
	public Article(Long topicId, String initialId, String title, String author, String source, String majorTopic) {
		this.topicId = topicId;
		this.initialId = initialId;
		this.title = title;
		this.author = author;
		this.source = source;
		this.majorTopic = majorTopic;
	}
}
