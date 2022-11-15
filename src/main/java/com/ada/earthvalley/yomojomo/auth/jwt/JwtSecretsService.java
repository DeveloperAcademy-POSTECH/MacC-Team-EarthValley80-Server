package com.ada.earthvalley.yomojomo.auth.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.common.propertyServices.JwtPropertyService;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtSecretsService extends JwtPropertyService {
	private static final long TOKEN_VALID_TIME = 10 * 60 * 60 * 1000L;

	public Date getIssuedAt() {
		return new Date();
	}

	public Date getExpiredAt() {
		return new Date(getIssuedAt().getTime() + TOKEN_VALID_TIME);
	}

	public Key getSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(super.secret));
	}
}
