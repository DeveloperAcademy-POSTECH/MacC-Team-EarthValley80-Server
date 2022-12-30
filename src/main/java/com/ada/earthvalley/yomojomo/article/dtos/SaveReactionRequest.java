package com.ada.earthvalley.yomojomo.article.dtos;

import java.util.List;

import lombok.Getter;

@Getter
public class SaveReactionRequest {
	private List<Reactions> reactionsList;

	public static class Reactions {
		private String emoji;
	}
}
