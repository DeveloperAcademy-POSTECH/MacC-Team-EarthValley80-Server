package com.ada.earthvalley.yomojomo.auth.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.auth.dtos.YomojomoToken;
import com.ada.earthvalley.yomojomo.auth.jwt.services.JwtUtilsService;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;
import com.ada.earthvalley.yomojomo.user.entities.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TokenDomainService {
	private final JwtUtilsService jwtUtilsService;

	public YomojomoToken createAccessToken(User user) {
		YomojomoClaim claim = YomojomoClaim.ofUser(user);

		// access token 생성
		String accessToken = jwtUtilsService.createAccessToken(claim);
		return YomojomoToken.createAccessToken(accessToken);
	}

	@Transactional
	public YomojomoToken createAndSaveRefreshToken(User user) {
		YomojomoClaim claim = YomojomoClaim.ofUser(user);

		// refresh token 생성 후 저장
		String refreshToken = jwtUtilsService.createRefreshToken(claim);
		user.setRefreshToken(refreshToken);
		return YomojomoToken.createRefreshToken(refreshToken);
	}
}
