package com.ada.earthvalley.yomojomo.activity.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.article.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
