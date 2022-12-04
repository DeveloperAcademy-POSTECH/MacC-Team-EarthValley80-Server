package com.ada.earthvalley.yomojomo.article.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubTopicType {
	// 시사
	CURRENT_AFFAIR("시사"),
	SOCIAl("사회"),
	WORLD("세계"),
	HISTORY("역사"),

	// 과학
	SCIENCE("과학"),
	ROBOT("로봇"),
	ENVIRONMENT("환경"),
	HEALTH("건강"),
	ANIMAL("동물"),
	SPACE("우주"),

	// 경제
	ECONOMY("경제"),
	COMPANY("회사"),
	IT("IT"),

	// 문화
	CULTURE("문화"),
	ARTS("예술"),
	BROADCAST("방송"),
	SPORTS("운동");

	private final String value;
}
