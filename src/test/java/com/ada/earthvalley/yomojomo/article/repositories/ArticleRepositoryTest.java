package com.ada.earthvalley.yomojomo.article.repositories;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.ada.earthvalley.yomojomo.BaseDataJpaTest;
import com.ada.earthvalley.yomojomo.article.entities.Article;

@DataJpaTest
class ArticleRepositoryTest extends BaseDataJpaTest {
	@Autowired
	ArticleRepository articleRepository;

	@DisplayName("findById - 성공")
	@Test
	void findById_success() throws Exception {
		Article article = Article.builder()
			.author(" author")
			.title("title")
			.initialId("1")
			.topicId(1L)
			.majorTopic("majorTopic")
			.source("source")
			.build();
		em.persist(article);
		flushAndClear();

		Article byId = articleRepository.findById(article.getId()).get();
		assertThat(byId).isNotNull();
	}

	@DisplayName("findByMajorTopic - 성공")
	@Sql("classpath:sql/article.sql")
	@Test
	void findByMajorTopic_success() throws Exception {
		// when
		List<Article> result = articleRepository.findByMajorTopicAndCreatedAtBetween("CURRENT_AFFAIR",
			LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now());

		// then
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getMajorTopic()).isEqualTo("CURRENT_AFFAIR");
		assertThat(result.get(0).getCreatedAt())
			.isBetween(LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now());

	}
}