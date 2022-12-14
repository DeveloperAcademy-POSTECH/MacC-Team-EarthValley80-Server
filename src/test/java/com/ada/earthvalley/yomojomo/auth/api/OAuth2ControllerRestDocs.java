package com.ada.earthvalley.yomojomo.auth.api;

import static com.ada.earthvalley.yomojomo.auth.AuthTestConst.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpHead;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ada.earthvalley.yomojomo.auth.configs.OAuth2WebSecurityConfig;
import com.ada.earthvalley.yomojomo.auth.dtos.LoginResponse;
import com.ada.earthvalley.yomojomo.auth.dtos.YomojomoToken;
import com.ada.earthvalley.yomojomo.auth.services.OAuth2ApiService;
import com.ada.earthvalley.yomojomo.configs.SpringRestDocsConfig;

@DisplayName("OAuth2Controller ?????????")
@ExtendWith(RestDocumentationExtension.class)
@ContextConfiguration(
	classes = {
		OAuth2WebSecurityConfig.class,
		OAuth2Controller.class
	},
	initializers = ConfigDataApplicationContextInitializer.class
)
@WebAppConfiguration
@WebMvcTest(OAuth2Controller.class)
class OAuth2ControllerRestDocs {
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private OAuth2Controller controller;
	@MockBean
	private OAuth2ApiService oAuth2ApiService;
	private MockMvc mvc;

	@BeforeEach
	public void setup(RestDocumentationContextProvider restDocumentation) {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.apply(SpringRestDocsConfig.configurer(restDocumentation))
			.build();
	}

	@WithMockUser
	@DisplayName("????????? - ??????")
	@Test
	void ?????????() throws Exception {
		// given
		YomojomoToken accessToken = YomojomoToken.createAccessToken("access_token");
		YomojomoToken refreshToken = YomojomoToken.createRefreshToken("refresh_token");
		LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);

		// when
		when(oAuth2ApiService.oauth2Login(null))
			.thenReturn(loginResponse);

		// then
		mvc.perform(
				RestDocumentationRequestBuilders
					.get(LOGIN_URI)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accessToken.token", is("access_token")))
			.andExpect(jsonPath("$.accessToken.iat").exists())
			.andExpect(jsonPath("$.accessToken.exp").exists())
			.andExpect(jsonPath("$.refreshToken.token", is("refresh_token")))
			.andExpect(jsonPath("$.refreshToken.iat").exists())
			.andExpect(jsonPath("$.refreshToken.exp").exists())
			.andDo(document(DOCS_OUTPUT_DIR,
				responseFields(
					fieldWithPath("accessToken").description("JWT ?????? ??????"),
					fieldWithPath("accessToken.token").description("?????? ???"),
					fieldWithPath("accessToken.iat").description("?????? ?????? ??????"),
					fieldWithPath("accessToken.exp").description("?????? ?????? ??????"),
					fieldWithPath("refreshToken").description("JWT ????????? ??????"),
					fieldWithPath("refreshToken.token").description("?????? ???"),
					fieldWithPath("refreshToken.iat").description("?????? ?????? ??????"),
					fieldWithPath("refreshToken.exp").description("?????? ?????? ??????")
				)
			));

		verify(oAuth2ApiService, times(1)).oauth2Login(null);
	}

	@WithMockUser
	@DisplayName("???????????? - ??????")
	@Test
	void ????????????() throws Exception {
		// given
		YomojomoToken accessToken = YomojomoToken.createAccessToken("access_token");
		YomojomoToken refreshToken = YomojomoToken.createRefreshToken("refresh_token");

		// when
		when(oAuth2ApiService.oauth2SignUp(null))
			.thenReturn(UUID.randomUUID().toString());

		// then
		mvc.perform(RestDocumentationRequestBuilders
				.post(SIGNUP_URI)
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andDo(document(DOCS_OUTPUT_DIR,
				responseHeaders(
					headerWithName(HttpHeaders.LOCATION).description("????????? ????????? URI")
				)
			));

		verify(oAuth2ApiService, times(1)).oauth2SignUp(null);
	}
}