package com.ada.earthvalley.yomojomo.auth.services;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.auth.dtos.YomojomoToken;
import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.dtos.LoginResponse;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.services.UserDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2ApiService {
	private final VendorResourceService vendorResourceService;
	private final TokenDomainService tokenDomainService;
	private final UserDomainService userDomainService;

	public LoginResponse oauth2Login(YomojomoOAuth2User user) throws YomojomoUserException {
		try {
			VendorResource vendorResource = vendorResourceService.findVendorResourceOrThrow(user);
			User userFromVendorResource = userDomainService.findByVendorResource(vendorResource);

			// 존재할 경우 access token 생성, refresh token 생성 후 저장
			YomojomoToken accessToken = tokenDomainService.createAccessToken(userFromVendorResource);
			YomojomoToken refreshToken = tokenDomainService.createAndSaveRefreshToken(userFromVendorResource);

			return new LoginResponse(accessToken, refreshToken);
		} catch (NoSuchElementException e) {
			throw new YomojomoAuthException(AuthError.NOT_A_MEMBER);
		}
	}

	public LoginResponse oauth2SignUp(YomojomoOAuth2User user) {
		User createdUser = userDomainService.createUserWithOAuth2(user);

		YomojomoToken accessToken = tokenDomainService.createAccessToken(createdUser);
		YomojomoToken refreshToken = tokenDomainService.createAndSaveRefreshToken(createdUser);

		return new LoginResponse(accessToken, refreshToken);
	}
}
