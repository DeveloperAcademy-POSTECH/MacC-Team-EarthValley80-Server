package com.ada.earthvalley.yomojomo.article.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ada.earthvalley.yomojomo.article.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Query("select a from Article a where a.majorTopic in :majorTopics and a.createdAt between :startDay and :endDay")
	List<Article> findAllByMajorTopicsAndCreatedAtBetween(@Param("majorTopics") List<String> majorTopics,
		@Param("startDay") LocalDateTime startOfWeek, @Param("endDay") LocalDateTime endOfWeek);

	List<Article> findAllByCreatedAtBetween(LocalDateTime startOfWeek, LocalDateTime endOfWeek);
}
