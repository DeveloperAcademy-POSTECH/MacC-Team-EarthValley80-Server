package com.ada.earthvalley.yomojomo.article.services;

import static com.ada.earthvalley.yomojomo.article.exceptions.ArticleError.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.article.dtos.FetchArticleDetailResponse;
import com.ada.earthvalley.yomojomo.article.dtos.FetchArticleListResponse;
import com.ada.earthvalley.yomojomo.article.dtos.SaveReactionRequest;
import com.ada.earthvalley.yomojomo.article.entities.Article;
import com.ada.earthvalley.yomojomo.article.entities.Reaction;
import com.ada.earthvalley.yomojomo.article.entities.enums.Emoji;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoArticleException;
import com.ada.earthvalley.yomojomo.article.repositories.ArticleRepository;
import com.ada.earthvalley.yomojomo.article.repositories.ReactionRepository;
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
	private final ReactionRepository reactionRepository;

	public FetchArticleListResponse getPersonalizedArticleLists(SecurityUser currentUser) {
		User user = userRepository.findById(currentUser.getId())
			.orElseThrow(() -> new YomojomoUserException(UserError.USER_NOT_FOUND));

		// TODO: 만약 user가 topic이 없을시 어떻게 할지(2022.12.05 - Daon)
		// TODO: 중복 topic 필터링 로직 최적화(2022.12.05 - Daon)
		List<UserTopic> topicList = user.getUserTopics();
		List<String> majorTopicList = topicList.stream().map(topic -> topic.getTopic().getMajorTopic().toString())
			.collect(Collectors.toList());

		Set<String> filteredTopics = new HashSet<>(majorTopicList);

		List<Article> articleList = articleRepository
			.findAllByMajorTopicsAndCreatedAtBetween(List.copyOf(filteredTopics),
				LocalDateTime.now().with(DayOfWeek.MONDAY),
				LocalDateTime.now()
			);

		return FetchArticleListResponse.ofList(articleList);
	}

	public FetchArticleListResponse getWeeklyArticleLists() {
		List<Article> articleList = articleRepository
			.findAllByCreatedAtBetween(
				LocalDateTime.now().with(DayOfWeek.MONDAY),
				LocalDateTime.now()
			);
		if (articleList.size() == 0) {
			throw new YomojomoArticleException(ARTICLE_NOT_FOUND);
		}

		return FetchArticleListResponse.ofList(articleList);
	}

	public FetchArticleDetailResponse getArticleDetail(Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new YomojomoArticleException(ARTICLE_NOT_FOUND));
		return FetchArticleDetailResponse.ofArticle(article);
	}

	@Transactional
	public void saveArticleReaction(Long articleId, SecurityUser currentUser, SaveReactionRequest reactionRequest) {
		User user = userRepository.findById(currentUser.getId())
			.orElseThrow(() -> new YomojomoUserException(UserError.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new YomojomoArticleException(ARTICLE_NOT_FOUND));

		reactionRequest.getReactionsList()
			.stream().forEach(emoji -> reactionRepository.save(Reaction.builder()
				.emoji(Emoji.valueOf(emoji.getEmoji())).articleId(articleId).user(user).build()));
		System.out.println("test");
	}
}
