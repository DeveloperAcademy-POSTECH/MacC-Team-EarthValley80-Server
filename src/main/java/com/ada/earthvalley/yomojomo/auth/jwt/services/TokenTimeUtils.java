package com.ada.earthvalley.yomojomo.auth.jwt.services;

import java.time.LocalDateTime;
import java.util.Date;

public class TokenTimeUtils {
	// Access Token 만료기한 - 1일
	// Refresh Token 만료기한 - 30일
	private static final long ACCESS_TOKEN_VALID_MILLI_TIME = 30 * 24 * 60 * 60 * 1000L;
	private static final long REFRESH_TOKEN_VALID_MILLI_TIME = 30 * 24 * 60 * 60 * 1000L;

	private static final long  ACCESS_TOKEN_VALID_TIME = 30 * 24 * 60 * 60L;
	private static final long  REFRESH_TOKEN_VALID_TIME = 30 * 24 * 60 * 60L;

	public static Date getAccessTokenExpiredDate() {
		return new Date(new Date().getTime() + ACCESS_TOKEN_VALID_MILLI_TIME);
	}

	public static Date getRefreshTokenExpiredDate() {
		return new Date(new Date().getTime() + REFRESH_TOKEN_VALID_MILLI_TIME);
	}

	public static LocalDateTime getAccessTokenLocalDateTime() {
		return LocalDateTime.now().plusSeconds(ACCESS_TOKEN_VALID_TIME);
	}

	public static LocalDateTime getRefreshTokenLocalDateTime() {
		return LocalDateTime.now().plusSeconds(REFRESH_TOKEN_VALID_TIME);
	}
}
