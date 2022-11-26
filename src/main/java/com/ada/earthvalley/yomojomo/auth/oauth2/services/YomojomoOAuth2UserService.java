package com.ada.earthvalley.yomojomo.auth.oauth2.services;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.auth.entities.enums.VendorType;
import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;

@Service
public class YomojomoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User delegateUser = delegate.loadUser(userRequest);
		YomojomoOAuth2User oAuth2User = new YomojomoOAuth2User(delegateUser);

		String vendorId = userRequest.getClientRegistration().getRegistrationId();
		switch (vendorId) {
			case ("kakao"):
				oAuth2User.setVendorType(VendorType.KAKAO);
			default:
		}
		return oAuth2User;
	}
}
