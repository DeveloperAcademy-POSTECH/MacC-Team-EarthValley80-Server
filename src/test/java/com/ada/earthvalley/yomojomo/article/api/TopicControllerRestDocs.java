package com.ada.earthvalley.yomojomo.article.api;

import static com.ada.earthvalley.yomojomo.article.TopicConst.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.apache.http.HttpHeaders;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.article.TopicConst;
import com.ada.earthvalley.yomojomo.article.TopicFixtures;
import com.ada.earthvalley.yomojomo.article.controllers.TopicController;
import com.ada.earthvalley.yomojomo.article.dtos.FetchTopicsResponse;
import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest;
import com.ada.earthvalley.yomojomo.article.entities.Topic;
import com.ada.earthvalley.yomojomo.article.services.TopicService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.configs.SpringRestDocsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TopicController.class)
@ExtendWith(RestDocumentationExtension.class)
public class TopicControllerRestDocs {
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
			.apply(SpringRestDocsConfig.configurer(contextProvider))
			.build();
	}

	@DisplayName("[GET] /api/topics")
	@Test
	void 모든_토픽_가져오기() throws Exception {
		// given
		List<Topic> topics = TopicFixtures.topics();
		FetchTopicsResponse response = FetchTopicsResponse.ofDomain(topics);

		// when
		when(topicService.fetchAllTopics())
			.thenReturn(response);

		// then
		mockMvc.perform(
				RestDocumentationRequestBuilders
					.get(TopicConst.FETCH_ALL_URI)
					.contentType(MediaType.APPLICATION_JSON)

			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.topics").exists())
			.andExpect(jsonPath("$.topics[0].id").exists())
			.andExpect(jsonPath("$.topics[1].majorTopic").exists())
			.andExpect(jsonPath("$.topics[2].subTopic").exists())
			.andDo(document(DOCS_OUTPUT_DIR,
				// requestHeaders(
				// 	headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
				// ),
				responseFields(
					fieldWithPath("topics").description("전체 토픽"),
					fieldWithPath("topics[].id").description("토픽의 id"),
					fieldWithPath("topics[].majorTopic").description("토픽 대분류"),
					fieldWithPath("topics[].subTopic").description("토픽 소분류")
				)
			));

	}

	@DisplayName("[POST] /api/interests")
	@Test
	void 관심있는_토픽_선택하기() throws Exception {
		// given
		String payload = objectMapper.writeValueAsString(TopicFixtures.request());

		// when
		doNothing()
			.when(topicService)
			.saveAllTopics(any(SecurityUser.class), any(SelectInterestsRequest.class));

		mockMvc.perform(
				RestDocumentationRequestBuilders
					.post(SAVE_ALL_URI)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(payload)
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.LOCATION, SAVE_ALL_URI))
			.andDo(document(DOCS_OUTPUT_DIR,
				requestFields(
					fieldWithPath("topics").description("유저가 선택한 토픽들"),
					fieldWithPath("topics[].id").description("토픽 id")
				)
			));
	}
}
