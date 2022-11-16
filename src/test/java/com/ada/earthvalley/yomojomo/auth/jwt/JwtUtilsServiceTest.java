package com.ada.earthvalley.yomojomo.auth.jwt;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
class JwtUtilsServiceTest {
	@Mock
	private JwtSecretsService jwtSecretsService;

	@InjectMocks
	private JwtUtilsService jwtUtilsService;
	private Key secretKey;

	@BeforeEach
	void init() {
		secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		when(jwtSecretsService.getIssuer())
			.thenReturn("yomojomo.com");
		when(jwtSecretsService.getSecretKey())
			.thenReturn(secretKey);
	}

	@DisplayName("parseJws - 실패 (secret key 불일치)")
	@Test
	void parseJws_failure_invalid_secret_key() {
		// given
		String jws = Jwts.builder()
			.signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
			.compact();

		// when, then
		assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(jwtUtilsService, "parseJws", jws))
			.isInstanceOf(YomojomoAuthException.class);
	}

	@DisplayName("parseJws - 실패 (issuer 불일치)")
	@Test
	void parseJws_failure_invalid_issuer() {
		// given
		String jws = Jwts.builder()
			.setIssuer("random_issuer")
			.signWith(jwtSecretsService.getSecretKey())
			.compact();

		// when, then
		assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(jwtUtilsService, "parseJws", jws))
			.isInstanceOf(YomojomoAuthException.class);
	}

	@DisplayName("parseJws - 실패 (토큰 만료)")
	@Test
	void parseJws_failure_expired() {
		// given
		String jws = Jwts.builder()
			.setIssuer(jwtSecretsService.getIssuer())
			.signWith(jwtSecretsService.getSecretKey())
			.setExpiration(new Date())
			.compact();

		// when, then
		assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(jwtUtilsService, "parseJws", jws))
			.isInstanceOf(YomojomoAuthException.class);
	}

	@DisplayName("parseJws - 성공")
	@Test
	void parseJws_success() {
		// given
		String jws = Jwts.builder()
			.setIssuer(jwtSecretsService.getIssuer())
			.signWith(jwtSecretsService.getSecretKey())
			.setSubject("subject")
			.claim("username", "leo")
			.setExpiration(new Date(new Date().getTime() + 60 * 1000L))
			.compact();

		// when
		Jws<Claims> parsedJws = (Jws<Claims>)ReflectionTestUtils.invokeMethod(jwtUtilsService, "parseJws", jws);
		Claims claims = parsedJws.getBody();
		JwsHeader header = parsedJws.getHeader();

		// then
		assertThat(claims.getSubject()).isEqualTo("subject");
		assertThat(claims.get("username")).isEqualTo("leo");
		assertThat(header.getAlgorithm()).isEqualTo(SignatureAlgorithm.HS256.toString());
	}

	@DisplayName("verify - 성공")
	@Test
	void verify_success() {
		// given
		UUID uuid = UUID.randomUUID();
		String jws = Jwts.builder()
			.setIssuer(jwtSecretsService.getIssuer())
			.signWith(jwtSecretsService.getSecretKey())
			.setSubject(uuid.toString())
			.setExpiration(new Date(new Date().getTime() + 60 * 1000L))
			.compact();

		// when
		YomojomoClaim verify = jwtUtilsService.verify(jws);

		// then
		assertThat(verify.getUserId()).isEqualTo(uuid.toString());
	}
}