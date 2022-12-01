package com.ada.earthvalley.yomojomo.article.repositories;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ada.earthvalley.yomojomo.article.entities.Topic;

@DataJpaTest
class TopicRepositoryTest {
	@Autowired
	TopicRepository topicRepository;

	@DisplayName("findAll - 성공")
	@Test
	void find_all_success() {
		// when, then
		List<Topic> allTopics = topicRepository.findAll();
		assertThat(allTopics).hasSize(17);
	}
}