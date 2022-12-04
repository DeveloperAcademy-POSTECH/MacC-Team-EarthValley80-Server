package com.ada.earthvalley.yomojomo.auth.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.auth.dtos.LoginResponse;
import com.ada.earthvalley.yomojomo.auth.dtos.TempAuthLoginRequest;
import com.ada.earthvalley.yomojomo.auth.dtos.YomojomoToken;
import com.ada.earthvalley.yomojomo.auth.exceptions.AuthError;
import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TempAuthService {
	private final TokenDomainService tokenDomainService;
	private final UserRepository userRepository;

	public LoginResponse login(TempAuthLoginRequest loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUserCode())
			.orElseThrow(() -> new YomojomoAuthException(AuthError.NOT_A_MEMBER));
		YomojomoToken accessToken = tokenDomainService.createAccessToken(user);
		YomojomoToken refreshToken = tokenDomainService.createAndSaveRefreshToken(user);
		return new LoginResponse(accessToken, refreshToken);
	}

	public void signUp(TempAuthLoginRequest loginRequest) {
		userRepository.findByUsername(loginRequest.getUserCode()).ifPresent(user -> {
			throw new YomojomoAuthException(AuthError.ALREADY_A_MEMBER);
		});
		User user = User.builder().username(loginRequest.getUserCode()).build();
		userRepository.save(user);
	}
}
