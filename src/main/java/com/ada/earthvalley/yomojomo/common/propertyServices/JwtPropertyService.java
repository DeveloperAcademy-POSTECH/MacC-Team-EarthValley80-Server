package com.ada.earthvalley.yomojomo.common.propertyServices;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

// TODO: @ConfigurationProperties 를 이용해 Refactoring (by Leo - 22.11.15)
public class JwtPropertyService {
	@Getter
	@Value("${jwt.claims.issuer}")
	protected String issuer;

	@Value("${jwt.secret-key}")
	protected String secret;
}
