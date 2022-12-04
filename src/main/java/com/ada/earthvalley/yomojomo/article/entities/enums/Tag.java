package com.ada.earthvalley.yomojomo.article.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tag {
	paragraph("paragraph"),
	title("title"),
	image("image"),
	pointSentence("pointSentence"),
	question("question"),
	space("space");

	private final String value;
}
