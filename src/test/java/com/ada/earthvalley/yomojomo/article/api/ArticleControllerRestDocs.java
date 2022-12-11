package com.ada.earthvalley.yomojomo.article.api;

import static com.ada.earthvalley.yomojomo.nie.NieTestConst.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.article.controllers.ArticleController;
import com.ada.earthvalley.yomojomo.article.dtos.FetchArticleListResponse;
import com.ada.earthvalley.yomojomo.article.entities.Article;
import com.ada.earthvalley.yomojomo.article.services.ArticleApiService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.configs.SpringRestDocsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ArticleController.class)
@ExtendWith(RestDocumentationExtension.class)
public class ArticleControllerRestDocs {
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ArticleController articleController;

	@MockBean
	ArticleApiService articleApiService;

	List<Article> articles = new ArrayList<>();

	@BeforeEach
	void init(RestDocumentationContextProvider contextProvider) {
		mockMvc = MockMvcBuilders
			.standaloneSetup(articleController)
			.apply(SpringRestDocsConfig.configurer(contextProvider))
			.build();

		Article article = Article.builder().author("일일일").imageUrl("spring").initialId("1232")
			.majorTopic("CURRNET_AFFAIR").source("source").title("title").topicId(1L).build();

		ReflectionTestUtils.setField(article, "id", 1L);
		articles.add(article);
		articles.add(article);
		articles.add(article);
	}

	@DisplayName("[GET] /api/articles")
	@Test
	void get_personalized_article() throws Exception {
		// given
		System.out.println(articles.size());
		FetchArticleListResponse response = FetchArticleListResponse.ofList(articles);
		System.out.println(response.getArticleInfoResponseList().get(0));

		// when
		when(articleApiService.getPersonalizedArticleLists(any(SecurityUser.class)))
			.thenReturn(response);

		// then
		mockMvc.perform(
				RestDocumentationRequestBuilders
					.get("/api/article")
					.contentType(MediaType.APPLICATION_JSON)

			)
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document(DOCS_OUTPUT_DIR,
				// requestHeaders(
				// 	headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
				// ),
				responseFields(
					fieldWithPath("articleInfoResponseList").description("아티클의 정보"),
					fieldWithPath("articleInfoResponseList[].id").description("아티클의 id"),
					fieldWithPath("articleInfoResponseList[].title").description("아티클의 제목"),
					fieldWithPath("articleInfoResponseList[].subTopic").description("아티클의 소분류 토픽"),
					fieldWithPath("articleInfoResponseList[].imageUrl").description("아티클의 image주소")
				)
			));
	}
}
