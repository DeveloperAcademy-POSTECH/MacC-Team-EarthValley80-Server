package com.ada.earthvalley.yomojomo.article.services;

import static com.ada.earthvalley.yomojomo.article.exceptions.ArticleError.*;
import static com.ada.earthvalley.yomojomo.article.exceptions.TopicError.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.activity.dtos.ReadingListResponse;
import com.ada.earthvalley.yomojomo.activity.dtos.ReadingListResponse.ReadingResponse;
import com.ada.earthvalley.yomojomo.activity.entities.Reading;
import com.ada.earthvalley.yomojomo.article.entities.Article;
import com.ada.earthvalley.yomojomo.article.entities.Topic;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoArticleException;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoTopicException;
import com.ada.earthvalley.yomojomo.article.repositories.ArticleRepository;
import com.ada.earthvalley.yomojomo.article.repositories.TopicRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleDomainService {
	private final ArticleRepository articleRepository;
	private final TopicRepository topicRepository;

	public List<ReadingResponse> articlesFromReadings(List<Reading> readings) {
		return readings.stream().map(reading -> {

			Article article = articleRepository.findById(reading.getArticleId())
				.orElseThrow(() -> new YomojomoArticleException(ARTICLE_NOT_FOUND));
			Topic topic = topicRepository.findById(article.getTopicId())
				.orElseThrow(() -> new YomojomoTopicException(TOPIC_NOT_FOUND));
			return new ReadingResponse(ReadingListResponse.TopicResponse.from(topic), article.getTitle(),
				article.getId());
		}).collect(Collectors.toList());
	}

}
