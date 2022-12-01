package com.ada.earthvalley.yomojomo.article.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.ada.earthvalley.yomojomo.article.entities.Topic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchTopicsResponse {
	private final List<TopicResponse> topics;

	@Getter
	public static class TopicResponse {
		private final long id;
		private final String majorTopic;
		private final String subTopic;

		public TopicResponse(Topic topic) {
			this.id = topic.getId();
			this.majorTopic = topic.getMajorTopic().getValue();
			this.subTopic = topic.getSubTopic().getValue();
		}
	}

	public static FetchTopicsResponse ofDomain(List<Topic> topics) {
		return new FetchTopicsResponse(
			topics.stream()
				.map(topic -> new TopicResponse(topic))
				.collect(Collectors.toList()));
	}
}
