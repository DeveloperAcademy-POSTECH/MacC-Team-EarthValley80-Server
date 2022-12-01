package com.ada.earthvalley.yomojomo.article.services;

import static com.ada.earthvalley.yomojomo.article.exceptions.TopicError.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.article.dtos.FetchTopicsResponse;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoTopicException;
import com.ada.earthvalley.yomojomo.article.repositories.TopicRepository;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.entities.UserTopic;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.ada.earthvalley.yomojomo.user.services.UserDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TopicService {
	private final TopicRepository topicRepository;
	private final UserDomainService userDomainService;

	public FetchTopicsResponse fetchAllTopics() {
		return FetchTopicsResponse.ofDomain(topicRepository.findAll());
	}

	@Transactional
	public void saveAllTopics(SecurityUser securityUser, SelectInterestsRequest request) throws
		YomojomoUserException,
		YomojomoTopicException {
		// TODO: 유저 존재 여부 확인은 나중에 필터 layer 로 옮김 (by Leo - 22.11.30)
		User user = userDomainService.findByIdOrElseThrow(securityUser.getId());
		request.getTopics()
			.stream()
			.map(interest -> topicRepository.findById(interest.getId())
				.orElseThrow(() -> new YomojomoTopicException(TOPIC_NOT_FOUND)))
			.forEach(topic -> {
				UserTopic.create(user, topic);
			});
	}
}
