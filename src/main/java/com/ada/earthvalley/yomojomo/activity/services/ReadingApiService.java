package com.ada.earthvalley.yomojomo.activity.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.activity.dtos.CreateReadingRequest;
import com.ada.earthvalley.yomojomo.activity.dtos.ReadingListResponse;
import com.ada.earthvalley.yomojomo.activity.entities.Reading;
import com.ada.earthvalley.yomojomo.activity.entities.enums.ReadingStatus;
import com.ada.earthvalley.yomojomo.activity.repositories.ReadingRepository;
import com.ada.earthvalley.yomojomo.article.exceptions.ArticleError;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoArticleException;
import com.ada.earthvalley.yomojomo.article.repositories.ArticleRepository;
import com.ada.earthvalley.yomojomo.article.services.ArticleDomainService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.exceptions.UserError;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.ada.earthvalley.yomojomo.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReadingApiService {
	private final ReadingDomainService readingDomainService;
	private final ArticleDomainService articleDomainService;
	private final ReadingRepository readingRepository;
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;

	public ReadingListResponse fetchUsersReading(SecurityUser user) {
		List<Reading> readings = readingDomainService.fetchUsersReadingActivityNotFinished(user);
		return new ReadingListResponse(articleDomainService.articlesFromReadings(readings));
	}

	public Long createReadingService(SecurityUser currentUser,
		CreateReadingRequest createReadingRequest, Long articleId) {
		User user = userRepository.findById(currentUser.getId())
			.orElseThrow(() -> new YomojomoUserException(UserError.USER_NOT_FOUND));
		articleRepository.findById(articleId)
			.orElseThrow(() -> new YomojomoArticleException(ArticleError.ARTICLE_NOT_FOUND));

		Reading reading = readingRepository.save(Reading.builder().articleId(articleId)
			.status(ReadingStatus.valueOf(createReadingRequest.getStatus())).user(user).build());
		return reading.getId();
	}
}
