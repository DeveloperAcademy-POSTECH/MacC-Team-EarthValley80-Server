package com.ada.earthvalley.yomojomo.auth.jwt;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;

class BearerAuthenticationConverterTest {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private BearerAuthenticationConverter converter;
	private MockHttpServletRequest request;

	@BeforeEach()
	void init() {
		converter = new BearerAuthenticationConverter();
		request = new MockHttpServletRequest();
	}

	@Test
	@DisplayName(".convert() 실패 - header 가 없음")
	void convert_fail_header_null() {
		// when, then
		assertThat(converter.convert(request)).isNull();
	}

	@Test
	@DisplayName(".convert() 실패 - header 가 두 덩이가 아님")
	void convert_fail_header_not_two() {
		// given
		request.addHeader("Authorization", " bearer token token");

		// when, then
		YomojomoAuthException exception = assertThrows(YomojomoAuthException.class,
			() -> converter.convert(request));
		assertThat(exception.getMessage()).isEqualTo(ErrorCode.ERR2000.getMessage());
	}

	@Test
	@DisplayName(".convert() 실패 - header 가 Bearer 가 아님")
	void convert_fail_not_bearer() {
		// given
		request.addHeader(AUTHORIZATION_HEADER, "Basic LeoBang");
		// when, then
		YomojomoAuthException exception = assertThrows(YomojomoAuthException.class,
			() -> converter.convert(request));
		assertThat(exception.getMessage()).isEqualTo(ErrorCode.ERR2000.getMessage());
	}

	@Test
	@DisplayName(".convert() 성공")
	void convert_success() {
		// given
		request.addHeader(AUTHORIZATION_HEADER, "Bearer tokenExample");
		// when
		JwtAuthenticationToken auth = converter.convert(request);
		System.out.println(auth.getCredentials());

		// then
		assertThat(auth.getCredentials()).isEqualTo("tokenExample");
		assertThat(auth.isAuthenticated()).isFalse();
	}
}