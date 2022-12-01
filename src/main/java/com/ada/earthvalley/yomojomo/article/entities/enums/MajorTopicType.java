package com.ada.earthvalley.yomojomo.article.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MajorTopicType {
	CURRENT_AFFAIR("시사"),
	SCIENCE("과학"),
	ECONOMY("경제"),
	CULTURE("문화");

	private final String value;
}
