package com.ada.earthvalley.yomojomo.auth.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.dtos.LoginResponse;
import com.ada.earthvalley.yomojomo.auth.dtos.YomojomoToken;
import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.services.UserDomainService;

@DisplayName("OAuth2ApiService 테스트")
@ExtendWith(MockitoExtension.class)
class OAuth2ApiServiceTest {
	@InjectMocks
	OAuth2ApiService oAuth2ApiService;
	@Mock
	VendorResourceService vendorResourceService;
	@Mock
	TokenDomainService tokenDomainService;
	@Mock
	UserDomainService userDomainService;

	@DisplayName("oauth2Login - 실패 (회원가입이 안 되어있는 유저)")
	@Test
	void oauth2Login_fail_not_authorized() {
		// when
		when(vendorResourceService.findVendorResourceOrThrow(any(YomojomoOAuth2User.class)))
			.thenThrow(NoSuchElementException.class);

		// then
		assertThatThrownBy(() -> {
			oAuth2ApiService.oauth2Login(mock(YomojomoOAuth2User.class));
		})
			.isInstanceOf(YomojomoAuthException.class);
	}

	@DisplayName("oauth2Login - 성공")
	@Test
	void oauth2Login_success() throws Exception {
		// given
		Constructor<VendorResource> constructor = VendorResource.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		VendorResource vendorResource = constructor.newInstance();
		final String accessToken = "access_token_string";
		final String refreshToken = "refresh_token_string";
		User user = new User();
		YomojomoToken yomojomoAccessToken = YomojomoToken.createAccessToken(accessToken);
		YomojomoToken yomojomoRefreshToken = YomojomoToken.createRefreshToken(refreshToken);

		// when
		when(vendorResourceService.findVendorResourceOrThrow(any(YomojomoOAuth2User.class)))
			.thenReturn(vendorResource);
		when(userDomainService.findByVendorResource(any(VendorResource.class)))
			.thenReturn(user);
		when(tokenDomainService.createAccessToken(any(User.class)))
			.thenReturn(yomojomoAccessToken);
		when(tokenDomainService.createAndSaveRefreshToken(any(User.class)))
			.thenReturn(yomojomoRefreshToken);

		LoginResponse loginResponse = oAuth2ApiService.oauth2Login(mock(YomojomoOAuth2User.class));

		// then
		assertThat(loginResponse.getAccessToken().getToken()).isEqualTo(accessToken);
		assertThat(loginResponse.getRefreshToken().getToken()).isEqualTo(refreshToken);
		assertThat(loginResponse.getAccessToken().getExp().isBefore(loginResponse.getRefreshToken().getExp())).isTrue();
	}

	@DisplayName("oauth2SignUp - 실패 (이미 가입한 회원")
	@Test
	void oauth2SignUp_fail_already_exist() {
		// when
		doThrow(new IllegalStateException())
			.when(vendorResourceService).throwIfVendorResourceExist(any(YomojomoOAuth2User.class));

		// then
		assertThatThrownBy(() -> {
			oAuth2ApiService.oauth2SignUp(mock(YomojomoOAuth2User.class));

		})
			.isInstanceOf(YomojomoAuthException.class);
	}

	@DisplayName("oauth2SignUp - 성공")
	@Test
	void oauth2SignUp_success() {
		// given
		User user = new User();
		ReflectionTestUtils.setField(user, "id", UUID.randomUUID());

		// when
		doNothing().when(vendorResourceService)
			.throwIfVendorResourceExist(any(YomojomoOAuth2User.class));
		when(userDomainService.createUserWithOAuth2(any(YomojomoOAuth2User.class)))
			.thenReturn(user);

		String userId = oAuth2ApiService.oauth2SignUp(mock(YomojomoOAuth2User.class));

		// then
		assertThat(userId).isEqualTo(user.getId().toString());
	}
}