package com.ada.earthvalley.yomojomo.auth.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.entities.enums.VendorType;
import com.ada.earthvalley.yomojomo.auth.repositories.VendorResourceRepository;

@DisplayName("VendorResourceService 테스트")
@ExtendWith(MockitoExtension.class)
class VendorResourceServiceTest {
	@InjectMocks
	VendorResourceService vendorResourceService;

	@Mock
	VendorResourceRepository vendorResourceRepository;

	@DisplayName("findVendorResourceOrThrow - 실패 (결과 없음)")
	@Test
	void findVendorResourceOrThrow_fail_no_result() throws Exception {
		// given
		OAuth2User mock = mock(OAuth2User.class);
		YomojomoOAuth2User user = new YomojomoOAuth2User(mock);
		ReflectionTestUtils.setField(user, "name", "123456");
		user.setVendorType(VendorType.KAKAO);

		when(vendorResourceRepository.findByVendorIdAndType(anyString(), any(VendorType.class)))
			.thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			vendorResourceService.findVendorResourceOrThrow(user);
		})
			.isInstanceOf(NoSuchElementException.class);
	}

	@DisplayName("findVendorResourceOrThrow - 성공")
	@Test
	void findVendorResourceOrThrow_success() throws Exception {
		// given
		OAuth2User mock = mock(OAuth2User.class);
		YomojomoOAuth2User user = new YomojomoOAuth2User(mock);
		ReflectionTestUtils.setField(user, "name", "123456");
		user.setVendorType(VendorType.KAKAO);

		VendorResource vendorResource = mock(VendorResource.class);

		// when
		when(vendorResourceRepository.findByVendorIdAndType(anyString(), any(VendorType.class)))
			.thenReturn(Optional.of(vendorResource));
		VendorResource resultVendorResource = vendorResourceService.findVendorResourceOrThrow(user);

		// then
		assertThat(resultVendorResource).isNotNull();
	}

	@DisplayName("throwIfVendorResourceExist - 실패 (이미 존재함)")
	@Test
	void throwIfVendorResourceExist_실패_이미존재함() {
		// given
		YomojomoOAuth2User mock = mock(YomojomoOAuth2User.class);

		// when
		when(mock.getSocialId())
			.thenReturn("string");
		when(mock.getVendorType())
			.thenReturn(VendorType.KAKAO);
		when(vendorResourceRepository.findByVendorIdAndType(anyString(), any(VendorType.class)))
			.thenReturn(Optional.of(mock(VendorResource.class)));

		// then
		assertThatThrownBy(() -> {
			vendorResourceService.throwIfVendorResourceExist(mock);
		})
			.isInstanceOf(IllegalStateException.class);
	}
}