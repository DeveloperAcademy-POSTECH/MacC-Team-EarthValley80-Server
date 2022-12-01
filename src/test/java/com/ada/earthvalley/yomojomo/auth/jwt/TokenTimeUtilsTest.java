package com.ada.earthvalley.yomojomo.auth.jwt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ada.earthvalley.yomojomo.auth.jwt.services.TokenTimeUtils;

@DisplayName("TokenTimeUtils 테스트")
class TokenTimeUtilsTest {

	@DisplayName("Refresh Token 시간 - 성공 (30일)")
	@Test
	void refresh_token_time() {
		// when
		Date refreshDate = TokenTimeUtils.getRefreshTokenExpiredDate();
		LocalDateTime refreshLocalDateTime = TokenTimeUtils.getRefreshTokenLocalDateTime();

		System.out.println("refreshDate = " + refreshDate);
		System.out.println("refreshLocalDateTime = " + refreshLocalDateTime);

		long refreshExpired = TimeUnit.DAYS.convert(refreshDate.getTime(), TimeUnit.MILLISECONDS);
		long todayDate = TimeUnit.DAYS.convert(new Date().getTime(), TimeUnit.MILLISECONDS);

		// then
		assertEquals(30, refreshExpired - todayDate);
		assertEquals(LocalDateTime.now().getDayOfMonth(), refreshLocalDateTime.minusDays(30).getDayOfMonth());
	}

	@DisplayName("Access Token 시간 - 성공 (1일)")
	@Test
	void access_token_time() {
		// when
		Date accessDate = TokenTimeUtils.getAccessTokenExpiredDate();
		LocalDateTime accessLocalDateTime = TokenTimeUtils.getAccessTokenLocalDateTime();

		System.out.println("accessDate = " + accessDate);
		System.out.println("accessLocalDateTime = " + accessLocalDateTime);

		long accessExpired = TimeUnit.DAYS.convert(accessDate.getTime(), TimeUnit.MILLISECONDS);
		long todayDate = TimeUnit.DAYS.convert(new Date().getTime(), TimeUnit.MILLISECONDS);

		// then
		assertEquals(1, accessExpired, todayDate);
		assertEquals(LocalDateTime.now().getDayOfMonth(), accessLocalDateTime.minusDays(1).getDayOfMonth());
	}
}