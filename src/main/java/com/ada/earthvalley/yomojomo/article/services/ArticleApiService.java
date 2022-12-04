package com.ada.earthvalley.yomojomo.article.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.article.dtos.FetchArticleListResponse;
import com.ada.earthvalley.yomojomo.article.entities.Article;
import com.ada.earthvalley.yomojomo.article.entities.enums.MajorTopicType;
import com.ada.earthvalley.yomojomo.article.repositories.ArticleRepository;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.entities.UserTopic;
import com.ada.earthvalley.yomojomo.user.exceptions.UserError;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.ada.earthvalley.yomojomo.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleApiService {
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;

	public FetchArticleListResponse getPersonalizedArticleLists(SecurityUser currentUser) {
		User user = userRepository.findById(currentUser.getId())
			.orElseThrow(() -> new YomojomoUserException(UserError.USER_NOT_FOUND));
		List<UserTopic> topicList = user.getUserTopics();
		List<MajorTopicType> majorTopicList = topicList.stream().map(topic -> topic.getTopic().getMajorTopic())
			.collect(Collectors.toList());

		Set<MajorTopicType> filteredTopics = new HashSet<>(majorTopicList);

		List<Article> articleList = articleRepository.findByMajorTopic(filteredTopics.toString());

		return FetchArticleListResponse.ofList(articleList);
	}
}
