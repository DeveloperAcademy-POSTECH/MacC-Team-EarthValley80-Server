package com.ada.earthvalley.yomojomo.article;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest.Interests;
import com.ada.earthvalley.yomojomo.article.entities.Topic;
import com.ada.earthvalley.yomojomo.article.entities.enums.MajorTopicType;
import com.ada.earthvalley.yomojomo.article.entities.enums.SubTopicType;

public class TopicFixtures {
	public static List<Topic> topics() throws Exception {
		List<Topic> topics = new ArrayList<>();
		Constructor<Topic> constructor = Topic.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		Topic topic1 = constructor.newInstance();
		ReflectionTestUtils.setField(topic1, "id", 1L);
		ReflectionTestUtils.setField(topic1, "majorTopic", MajorTopicType.CULTURE);
		ReflectionTestUtils.setField(topic1, "subTopic", SubTopicType.ANIMAL);

		Topic topic2 = constructor.newInstance();
		ReflectionTestUtils.setField(topic2, "id", 2L);
		ReflectionTestUtils.setField(topic2, "majorTopic", MajorTopicType.SCIENCE);
		ReflectionTestUtils.setField(topic2, "subTopic", SubTopicType.IT);

		Topic topic3 = constructor.newInstance();
		ReflectionTestUtils.setField(topic3, "id", 3L);
		ReflectionTestUtils.setField(topic3, "majorTopic", MajorTopicType.CURRENT_AFFAIR);
		ReflectionTestUtils.setField(topic3, "subTopic", SubTopicType.BROADCAST);

		Topic topic4 = constructor.newInstance();
		ReflectionTestUtils.setField(topic4, "id", 4L);
		ReflectionTestUtils.setField(topic4, "majorTopic", MajorTopicType.ECONOMY);
		ReflectionTestUtils.setField(topic4, "subTopic", SubTopicType.ARTS);

		topics.add(topic1);
		topics.add(topic2);
		topics.add(topic3);
		topics.add(topic4);
		return topics;
	}

	public static SelectInterestsRequest request() {
		Interests interest1 = new Interests(1L);
		Interests interest2 = new Interests(2L);
		Interests interest3 = new Interests(3L);
		Interests interest4 = new Interests(4L);
		return new SelectInterestsRequest(
			List.of(interest1, interest2, interest3, interest4));
	}
}
