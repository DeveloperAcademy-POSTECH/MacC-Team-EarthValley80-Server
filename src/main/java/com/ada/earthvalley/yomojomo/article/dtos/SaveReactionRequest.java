package com.ada.earthvalley.yomojomo.article.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReactionRequest {
	private List<Reactions> reactionsList;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Reactions {
		private String emoji;
	}
}
