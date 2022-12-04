package com.ada.earthvalley.yomojomo.activity.dtos;

import java.util.List;

import com.ada.earthvalley.yomojomo.article.entities.Topic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingListResponse {
	private List<ReadingResponse> readings;

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReadingResponse {
		private TopicResponse topic;
		private String title;
		private Long articleId;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TopicResponse {
		private Long topicId;
		private String subTopic;
		private String majorTopic;

		public static TopicResponse from(Topic topic) {
			return new TopicResponse(topic.getId(), topic.getSubTopic().getValue(), topic.getMajorTopic().getValue());
		}
	}
}
