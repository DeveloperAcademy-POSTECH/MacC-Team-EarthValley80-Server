package com.ada.earthvalley.yomojomo.auth.jwt.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.auth.jwt.enums.TokenType;
import com.ada.earthvalley.yomojomo.common.propertyServices.JwtPropertyService;

import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtSecretsService extends JwtPropertyService {
	public Date getIssuedAt() {
		return new Date();
	}

	public Date getExpiredAt(TokenType type) {
		switch (type) {
			case ACCESS_TOKEN:
				return TokenTimeUtils.getAccessTokenExpiredDate();
			case REFRESH_TOKEN:
				return TokenTimeUtils.getRefreshTokenExpiredDate();
			default:
				return new Date();
		}
	}

	public Key getSecretKey() throws DecodingException {
		return Keys.hmacShaKeyFor(super.secret.getBytes(StandardCharsets.UTF_8));
	}
}
