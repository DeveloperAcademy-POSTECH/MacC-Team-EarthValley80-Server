package com.ada.earthvalley.yomojomo.activity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MainSentence {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "main_sentence_id")
	private Long id;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id")
	private Activity activity;

	private int paragraphIndex;
	private int sentenceIndex;

	public MainSentence(int paragraphIndex, int sentenceIndex) {
		this.paragraphIndex = paragraphIndex;
		this.sentenceIndex = sentenceIndex;
	}
}
