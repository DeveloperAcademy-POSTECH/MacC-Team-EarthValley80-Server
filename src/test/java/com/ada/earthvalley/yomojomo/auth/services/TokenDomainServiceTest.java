package com.ada.earthvalley.yomojomo.auth.services;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.auth.dtos.YomojomoToken;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;
import com.ada.earthvalley.yomojomo.auth.jwt.services.JwtUtilsService;
import com.ada.earthvalley.yomojomo.auth.jwt.services.TokenTimeUtils;
import com.ada.earthvalley.yomojomo.user.entities.User;

@DisplayName("TokenDomainService 테스트")
@ExtendWith(MockitoExtension.class)
class TokenDomainServiceTest {
	@InjectMocks
	TokenDomainService tokenDomainService;
	@Mock
	JwtUtilsService jwtUtilsService;

	@DisplayName("createAccessToken - 성공")
	@Test
	void createAccessToken() {
		// given
		User user = new User();
		ReflectionTestUtils.setField(user, "id", UUID.randomUUID());
		final String tokenString = "access_token_string";

		// when
		when(jwtUtilsService.createAccessToken(any(YomojomoClaim.class)))
			.thenReturn(tokenString);

		YomojomoToken accessToken = tokenDomainService.createAccessToken(user);

		// then
		assertThat(accessToken).isNotNull();
		assertThat(accessToken.getToken()).isEqualTo(tokenString);
		assertThat(accessToken.getExp().isBefore(TokenTimeUtils.getRefreshTokenLocalDateTime()));
	}

	@DisplayName("createAndSaveRefreshToken - 성공")
	@Test
	void createAndSaveRefreshToken_success() {
		// given
		User user = new User();
		ReflectionTestUtils.setField(user, "id", UUID.randomUUID());
		final String tokenString = "refresh_token_string";

		// when
		when(jwtUtilsService.createRefreshToken(any(YomojomoClaim.class)))
			.thenReturn(tokenString);

		YomojomoToken refreshToken = tokenDomainService.createAndSaveRefreshToken(user);

		// then
		assertThat(refreshToken).isNotNull();
		assertThat(refreshToken.getToken()).isEqualTo(tokenString);
		assertThat(refreshToken.getExp().isAfter(TokenTimeUtils.getRefreshTokenLocalDateTime()));
		assertThat(user.getRefreshToken().getRefreshToken()).isEqualTo(tokenString);
	}
}