package com.ada.earthvalley.yomojomo.article.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {
	@Id
	@GeneratedValue
	@Column(name = "article_id")
	private Long id;

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
}
