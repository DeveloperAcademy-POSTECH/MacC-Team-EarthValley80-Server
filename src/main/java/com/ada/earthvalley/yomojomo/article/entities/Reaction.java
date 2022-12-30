package com.ada.earthvalley.yomojomo.article.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ada.earthvalley.yomojomo.article.entities.enums.Emoji;
import com.ada.earthvalley.yomojomo.user.entities.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Reaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reading_id")
	private Long id;

	private Emoji emoji;

	private Long articleId;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Reaction(Emoji emoji, Long articleId, User user) {
		this.emoji = emoji;
		this.articleId = articleId;
		setUser(user);
	}
}
