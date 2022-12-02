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

@DisplayName("OAuth2Controller 테스트")
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
	@DisplayName("로그인 - 성공")
	@Test
	void 로그인() throws Exception {
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
					fieldWithPath("accessToken").description("JWT 인증 토큰"),
					fieldWithPath("accessToken.token").description("토큰 값"),
					fieldWithPath("accessToken.iat").description("토큰 발행 시점"),
					fieldWithPath("accessToken.exp").description("토큰 만료 시점"),
					fieldWithPath("refreshToken").description("JWT 재발급 토큰"),
					fieldWithPath("refreshToken.token").description("토큰 값"),
					fieldWithPath("refreshToken.iat").description("토큰 발행 시점"),
					fieldWithPath("refreshToken.exp").description("토큰 만료 시점")
				)
			));

		verify(oAuth2ApiService, times(1)).oauth2Login(null);
	}

	@WithMockUser
	@DisplayName("회원가입 - 성공")
	@Test
	void 회원가입() throws Exception {
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
					headerWithName(HttpHeaders.LOCATION).description("생성된 유저의 URI")
				)
			));

		verify(oAuth2ApiService, times(1)).oauth2SignUp(null);
	}
}