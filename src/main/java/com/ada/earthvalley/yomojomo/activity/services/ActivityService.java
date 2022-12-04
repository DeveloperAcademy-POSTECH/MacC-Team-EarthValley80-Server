package com.ada.earthvalley.yomojomo.activity.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.activity.dtos.CreateActivityRequest;
import com.ada.earthvalley.yomojomo.activity.dtos.ReadingListResponse;
import com.ada.earthvalley.yomojomo.activity.dtos.ReadingListResponse.ReadingResponse;
import com.ada.earthvalley.yomojomo.activity.entities.Activity;
import com.ada.earthvalley.yomojomo.activity.entities.MainSentence;
import com.ada.earthvalley.yomojomo.article.entities.Article;
import com.ada.earthvalley.yomojomo.article.entities.Topic;
import com.ada.earthvalley.yomojomo.article.exceptions.ArticleError;
import com.ada.earthvalley.yomojomo.article.exceptions.TopicError;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoArticleException;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoTopicException;
import com.ada.earthvalley.yomojomo.article.repositories.ArticleRepository;
import com.ada.earthvalley.yomojomo.article.repositories.TopicRepository;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.exceptions.UserError;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.ada.earthvalley.yomojomo.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ActivityService {
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;
	private final TopicRepository topicRepository;

	@Transactional
	public void createActivity(SecurityUser user, Long articleId, CreateActivityRequest request) {
		User findUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new YomojomoUserException(UserError.USER_NOT_FOUND));
		articleRepository.findById(articleId)
			.orElseThrow(() -> new YomojomoArticleException(ArticleError.ARTICLE_NOT_FOUND));
		Activity activity = new Activity(articleId, request.getSummary().getContent());
		request.getMainSentences().forEach(m -> {
			MainSentence mainSentence = new MainSentence(m.getParagraphIndex(), m.getSentenceIndex());
			activity.addMainSentences(mainSentence);
		});
		findUser.addActivities(activity);
	}

	public ReadingListResponse fetchActivities(SecurityUser user) {
		User findUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new YomojomoUserException(UserError.USER_NOT_FOUND));
		List<ReadingResponse> readingList = findUser.getActivities().stream()
			.map(activity -> {
				Article article = articleRepository.findById(activity.getArticleId())
					.orElseThrow(() -> new YomojomoArticleException(ArticleError.ARTICLE_NOT_FOUND));
				Topic topic = topicRepository.findById(article.getTopicId())
					.orElseThrow(() -> new YomojomoTopicException(TopicError.TOPIC_NOT_FOUND));
				return new ReadingResponse(ReadingListResponse.TopicResponse.from(topic), article.getTitle(),
					article.getId());
			})
			.collect(Collectors.toList());
		return new ReadingListResponse(readingList);
	}
}
