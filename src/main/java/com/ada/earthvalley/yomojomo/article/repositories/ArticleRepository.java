package com.ada.earthvalley.yomojomo.article.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.article.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findByMajorTopic(String majorTopic);
}
