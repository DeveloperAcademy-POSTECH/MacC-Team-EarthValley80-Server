package com.ada.earthvalley.yomojomo.article.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.article.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findAllByMajorTopicAndCreatedAtBetween(String majorTopic,
		LocalDateTime startOfWeek, LocalDateTime endOfWeek);

	List<Article> findAllByCreatedAtBetween(LocalDateTime startOfWeek, LocalDateTime endOfWeek);
}
