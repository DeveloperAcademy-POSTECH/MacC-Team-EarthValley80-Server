package com.ada.earthvalley.yomojomo.auth.dtos;

import java.time.LocalDateTime;

import com.ada.earthvalley.yomojomo.auth.jwt.services.TokenTimeUtils;

import lombok.Data;

@Data
public class YomojomoToken {
	private String token;
	private LocalDateTime iat;
	private LocalDateTime exp;

	public static YomojomoToken createAccessToken(String accessToken) {
		YomojomoToken yomojomoToken = new YomojomoToken(accessToken);
		yomojomoToken.exp = TokenTimeUtils.getAccessTokenLocalDateTime();
		return yomojomoToken;
	}

	public static YomojomoToken createRefreshToken(String refreshToken) {
		YomojomoToken yomojomoToken = new YomojomoToken(refreshToken);
		yomojomoToken.exp = TokenTimeUtils.getRefreshTokenLocalDateTime();
		return yomojomoToken;
	}

	private YomojomoToken(String token) {
		this.token = token;
		this.iat = LocalDateTime.now();
	}
}

