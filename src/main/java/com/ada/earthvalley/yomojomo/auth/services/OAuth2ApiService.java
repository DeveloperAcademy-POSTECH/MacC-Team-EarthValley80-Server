package com.ada.earthvalley.yomojomo.auth.services;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.auth.dtos.YomojomoToken;
import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.dtos.LoginResponse;
import com.ada.earthvalley.yomojomo.auth.exceptions.AuthError;
import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
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

	public String oauth2SignUp(YomojomoOAuth2User user) throws YomojomoAuthException {
		try {
			vendorResourceService.throwIfVendorResourceExist(user);
			return userDomainService.createUserWithOAuth2(user).getIdString();
		} catch (IllegalStateException e) {
			throw new YomojomoAuthException(AuthError.ALREADY_A_MEMBER);
		}
	}
}
