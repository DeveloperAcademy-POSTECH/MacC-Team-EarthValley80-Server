package com.ada.earthvalley.yomojomo.article.integration;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ada.earthvalley.yomojomo.BaseIntegrationTest;
import com.ada.earthvalley.yomojomo.article.TopicConst;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest.Interests;
import com.ada.earthvalley.yomojomo.user.entities.User;

@DisplayName("Topic API 통합 테스트 ")
public class TopicApiIntegrationTest extends BaseIntegrationTest {

	@DisplayName("모든 토픽 가져오기 - 성공")
	@Test
	void fetch_all_topics_success() throws Exception {
		// when, then
		mockMvc.perform(MockMvcRequestBuilders.get(TopicConst.FETCH_ALL_URI)
				.header(HttpHeaders.AUTHORIZATION, testUserAccessToken)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.topics", hasSize(17)));
	}

	@DisplayName("토픽 선택하기 - 성공")
	@Test
	void select_topics_success() throws Exception {
		// given
		Interests interest1 = new Interests(1L);
		Interests interest2 = new Interests(5L);
		Interests interest3 = new Interests(15L);
		SelectInterestsRequest request = new SelectInterestsRequest(
			List.of(interest1, interest2, interest3));

		String requestString = objectMapper.writeValueAsString(request);

		// when, then
		mockMvc.perform(MockMvcRequestBuilders.post(TopicConst.SAVE_ALL_URI)
				.header(HttpHeaders.AUTHORIZATION, testUserAccessToken)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestString)
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().exists(HttpHeaders.LOCATION));

		// Data Test
		User findUser = em.find(User.class, testUser.getId());
		assertThat(findUser.getUserTopics()).hasSize(3);
	}
}