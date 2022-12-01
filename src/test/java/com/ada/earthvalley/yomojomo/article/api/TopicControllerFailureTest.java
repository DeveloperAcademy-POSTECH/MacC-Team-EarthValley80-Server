package com.ada.earthvalley.yomojomo.article.api;

import static com.ada.earthvalley.yomojomo.article.TopicConst.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.article.TopicFixtures;
import com.ada.earthvalley.yomojomo.article.controllers.TopicController;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest;
import com.ada.earthvalley.yomojomo.article.exceptions.TopicError;
import com.ada.earthvalley.yomojomo.article.exceptions.YomojomoTopicException;
import com.ada.earthvalley.yomojomo.article.services.TopicService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.common.exceptions.handler.GlobalExceptionHandler;
import com.ada.earthvalley.yomojomo.configs.SpringRestDocsConfig;
import com.ada.earthvalley.yomojomo.user.exceptions.UserError;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TopicController.class)
@ExtendWith(RestDocumentationExtension.class)
public class TopicControllerFailureTest {
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	TopicController topicController;
	@MockBean
	TopicService topicService;

	@BeforeEach
	void init(RestDocumentationContextProvider contextProvider) {
		mockMvc = MockMvcBuilders
			.standaloneSetup(topicController)
			.setControllerAdvice(GlobalExceptionHandler.class)
			.apply(SpringRestDocsConfig.configurer(contextProvider))
			.build();
	}

	@DisplayName("selectInterests() - 실패 (유저 없음)")
	@Test
	void selectInterests_fail_no_user() throws Exception {
		// given
		String payload = objectMapper.writeValueAsString(TopicFixtures.request());

		// when
		doThrow(new YomojomoUserException(UserError.USER_NOT_FOUND))
			.when(topicService).saveAllTopics(any(SecurityUser.class), any(SelectInterestsRequest.class));

		mockMvc.perform(
				MockMvcRequestBuilders
					.post(SAVE_ALL_URI)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(payload)
			)
			.andDo(print())
			.andExpect(status().isNotFound());

	}

	@DisplayName("selectInterests() - 실패 (토픽 없음)")
	@Test
	void selectInterests_fail_no_topic() throws Exception {
		// given
		String payload = objectMapper.writeValueAsString(TopicFixtures.request());

		// when
		doThrow(new YomojomoTopicException(TopicError.TOPIC_NOT_FOUND))
			.when(topicService).saveAllTopics(any(SecurityUser.class), any(SelectInterestsRequest.class));

		mockMvc.perform(
				MockMvcRequestBuilders
					.post(SAVE_ALL_URI)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(payload)
			)
			.andDo(print())
			.andExpect(status().isNotFound());
	}

}
