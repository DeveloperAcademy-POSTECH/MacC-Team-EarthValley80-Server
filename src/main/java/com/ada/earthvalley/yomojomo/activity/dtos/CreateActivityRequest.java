package com.ada.earthvalley.yomojomo.activity.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateActivityRequest {
	private List<MainSentenceRequest> mainSentences;
	private SummaryRequest summary;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MainSentenceRequest {
		private int paragraphIndex;
		private int sentenceIndex;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SummaryRequest {
		private String content;
	}

}
