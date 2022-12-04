package com.ada.earthvalley.yomojomo.article.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectInterestsRequest {
	private List<Interests> topics;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Interests {
		private Long id;
	}
}
