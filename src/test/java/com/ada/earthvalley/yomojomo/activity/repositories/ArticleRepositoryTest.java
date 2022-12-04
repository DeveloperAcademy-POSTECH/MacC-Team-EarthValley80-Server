package com.ada.earthvalley.yomojomo.activity.repositories;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ada.earthvalley.yomojomo.BaseDataJpaTest;
import com.ada.earthvalley.yomojomo.article.entities.Article;

class ArticleRepositoryTest extends BaseDataJpaTest {
	@Autowired
	ArticleRepository articleRepository;

	@DisplayName("findById - 성공")
	@Test
	void findById_성공() throws Exception {
		Constructor<Article> constructor = Article.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		Article article = constructor.newInstance();
		em.persist(article);
		flushAndClear();

		Article byId = articleRepository.findById(article.getId()).get();
		assertThat(byId).isNotNull();
	}
}