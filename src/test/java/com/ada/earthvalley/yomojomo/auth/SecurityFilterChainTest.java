package com.ada.earthvalley.yomojomo.auth;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ada.earthvalley.yomojomo.auth.configs.WebSecurityConfig;
import com.ada.earthvalley.yomojomo.auth.exceptions.AuthError;
import com.ada.earthvalley.yomojomo.auth.jwt.AuthTestController;
import com.ada.earthvalley.yomojomo.auth.jwt.services.JwtSecretsService;
import com.ada.earthvalley.yomojomo.auth.jwt.services.JwtUtilsService;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;

import io.jsonwebtoken.Jwts;

@DisplayName("@YomojomoUser 테스트")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
	WebSecurityConfig.class,
	AuthTestController.class
})
@EnableWebMvc
@WebAppConfiguration
@TestPropertySource(properties = {
	"jwt.secret-key=secret_key_value_of_this_app_upper_than256_bits",
	"jwt.claims.issuer=issuer"
})
public class SecurityFilterChainTest {
	@Autowired
	WebApplicationContext context;
	@SpyBean
	JwtUtilsService jwtUtilsService;
	@SpyBean
	JwtSecretsService jwtSecretsService;
	MockMvc mockMvc;

	@BeforeEach
	void init() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();

	}

	@Test
	void controller_did_load() {
		// when, then
		AuthTestController bean = context.getBean(AuthTestController.class);
		assertThat(bean).isNotNull();
	}

	@DisplayName("@YomojomoUser - 성공")
	@Test
	void yomojomo_user_success() throws Exception {
		// given
		final UUID uuid = UUID.randomUUID();
		Constructor<YomojomoClaim> constructor = YomojomoClaim.class.getDeclaredConstructor(UUID.class);
		constructor.setAccessible(true);
		YomojomoClaim yomojomoClaim = constructor.newInstance(uuid);
		String jws = jwtUtilsService.createAccessToken(yomojomoClaim);
		assertThat(jws).isNotNull();

		// when, then
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/api/yomojomo-user")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + jws)
			)
			.andExpect(status().isOk())
			.andExpect(content().string(uuid.toString()))
			.andDo(print());
	}

	@DisplayName("SecurityFilterChain - 실패 (인증 헤더 없음)")
	@Test
	void security_filter_chain_failure_no_header() throws Exception {
		// when, then
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/api/yomojomo-user")
			)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.timestamp").exists())
			.andExpect(jsonPath("$.status").exists())
			.andExpect(jsonPath("$.code").value(AuthError.ILLEGAL_AUTH_HEADER.getCode()))
			.andExpect(jsonPath("$.message").value(AuthError.ILLEGAL_AUTH_HEADER.getMessage()))
			.andDo(print());
	}

	@DisplayName("SecurityFilterChain - 실패 (인증 헤더 형식에 맞지 않음)")
	@Test
	void security_filter_chain_failure_invalid_header() throws Exception {
		// when, then
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/api/yomojomo-user")
					.header(HttpHeaders.AUTHORIZATION, "Bearer abcds")
			)
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.timestamp").exists())
			.andExpect(jsonPath("$.status").exists())
			.andExpect(jsonPath("$.code").value(AuthError.INVALID_JWT.getCode()))
			.andExpect(jsonPath("$.message").value(AuthError.INVALID_JWT.getMessage()))
			.andDo(print());

	}

	@DisplayName("SecurityFilterChain - 실패 (인증 토큰 만료)")
	@Test
	void security_filter_chain_failure_expired() throws Exception {
		String jws = Jwts.builder()
			.setExpiration(new Date())
			.setIssuer(jwtSecretsService.getIssuer())
			.signWith(jwtSecretsService.getSecretKey())
			.compact();

		// when, then
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/api/yomojomo-user")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + jws)
			)
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.timestamp").exists())
			.andExpect(jsonPath("$.status").exists())
			.andExpect(jsonPath("$.code").value(AuthError.JWT_EXPIRED.getCode()))
			.andExpect(jsonPath("$.message").value(AuthError.JWT_EXPIRED.getMessage()))
			.andDo(print());
	}

}
