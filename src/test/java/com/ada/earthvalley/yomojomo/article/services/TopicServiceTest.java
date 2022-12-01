package com.ada.earthvalley.yomojomo.article.services;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.article.dtos.FetchTopicsResponse;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest.Interests;
import com.ada.earthvalley.yomojomo.article.entities.Topic;
import com.ada.earthvalley.yomojomo.article.entities.enums.MajorTopicType;
import com.ada.earthvalley.yomojomo.article.entities.enums.SubTopicType;
import com.ada.earthvalley.yomojomo.article.exceptions.TopicError;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoTopicException;
import com.ada.earthvalley.yomojomo.article.repositories.TopicRepository;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.exceptions.UserError;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.ada.earthvalley.yomojomo.user.repositories.UserRepository;
import com.ada.earthvalley.yomojomo.user.services.UserDomainService;

@DisplayName("TopicService 테스트")
@ExtendWith(MockitoExtension.class)
class TopicServiceTest {
	@InjectMocks
	TopicService topicService;

	@Mock
	TopicRepository topicRepository;

	@Mock
	UserDomainService userDomainService;

	@Mock
	SecurityUser securityUser;

	List<Topic> topics = new ArrayList<>();

	@BeforeEach
	void init() throws Exception {
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
	}

	@DisplayName("fetchAllTopics - 성공")
	@Test
	void fetchAllTopics_success() throws Exception {
		// when
		when(topicRepository.findAll())
			.thenReturn(topics);

		FetchTopicsResponse response = topicService.fetchAllTopics();

		// then
		assertThat(response.getTopics()).hasSize(4);
		response.getTopics().forEach(topic -> {
			assertThat(topic.getId()).isNotNull();
			assertThat(topic.getMajorTopic()).isNotNull();
			assertThat(topic.getSubTopic()).isNotNull();
		});
	}

	@DisplayName("saveAllTopics - 실패 (유저 없음)")
	@Test
	void saveAllTopics_failure_no_user() {
		// given
		Interests interest1 = new Interests(1L);
		Interests interest2 = new Interests(2L);
		Interests interest3 = new Interests(3L);
		Interests interest4 = new Interests(4L);
		SelectInterestsRequest request = new SelectInterestsRequest(
			List.of(interest1, interest2, interest3, interest4));

		// when
		when(securityUser.getId())
			.thenReturn(UUID.randomUUID());
		when(userDomainService.findByIdOrElseThrow(any(UUID.class)))
			.thenThrow(new YomojomoUserException(UserError.USER_NOT_FOUND));

		// then
		assertThatThrownBy(() -> {
			topicService.saveAllTopics(securityUser, request);
		})
			.isInstanceOf(YomojomoUserException.class);
	}

	@DisplayName("saveAllTopics - 실패 (토픽 없음)")
	@Test
	void saveAllTopics_failure_no_topic() {
		// given
		Interests interest1 = new Interests(1L);
		Interests interest2 = new Interests(2L);
		Interests interest3 = new Interests(3L);
		Interests interest4 = new Interests(4L);
		SelectInterestsRequest request = new SelectInterestsRequest(
			List.of(interest1, interest2, interest3, interest4));

		// when
		when(securityUser.getId())
			.thenReturn(UUID.randomUUID());
		when(userDomainService.findByIdOrElseThrow(any(UUID.class)))
			.thenReturn(new User());
		when(topicRepository.findById(anyLong()))
			.thenThrow(new YomojomoTopicException(TopicError.TOPIC_NOT_FOUND));

		// then
		assertThatThrownBy(() -> {
			topicService.saveAllTopics(securityUser, request);
		})
			.isInstanceOf(YomojomoTopicException.class);
	}

	@DisplayName("saveAllTopics - 성공")
	@Test
	void saveAllTopics_success() {
		// given
		User user = new User();
		Interests interest1 = new Interests(1L);
		Interests interest2 = new Interests(2L);
		Interests interest3 = new Interests(3L);
		Interests interest4 = new Interests(4L);
		SelectInterestsRequest request = new SelectInterestsRequest(
			List.of(interest1, interest2, interest3, interest4));

		// when
		when(securityUser.getId())
			.thenReturn(UUID.randomUUID());
		when(userDomainService.findByIdOrElseThrow(any(UUID.class)))
			.thenReturn(user);
		when(topicRepository.findById(anyLong()))
			.thenReturn(Optional.of(topics.get(0)))
			.thenReturn(Optional.of(topics.get(1)))
			.thenReturn(Optional.of(topics.get(2)))
			.thenReturn(Optional.of(topics.get(3)));

		// then
		topicService.saveAllTopics(securityUser, request);
		assertThat(user.getUserTopics()).hasSize(4);

		verify(topicRepository, times(4)).findById(anyLong());
	}
}