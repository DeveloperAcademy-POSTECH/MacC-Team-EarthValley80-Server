package com.ada.earthvalley.yomojomo.user.services;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.ada.earthvalley.yomojomo.user.repositories.UserRepository;

@DisplayName("UserDomainService 테스트")
@ExtendWith(MockitoExtension.class)
class UserDomainServiceTest {
	@InjectMocks
	UserDomainService userDomainService;

	@Mock
	UserRepository userRepository;

	@DisplayName("findByIdOrElseThrow - 실패 (유저 없음)")
	@Test
	void findByIdOrElseThrow_failure_no_user() {
		// when
		when(userRepository.findById(any(UUID.class)))
			.thenReturn(Optional.empty());

		// then
		assertThatThrownBy(() -> {
			userDomainService.findByIdOrElseThrow(UUID.randomUUID());
		})
			.isInstanceOf(YomojomoUserException.class);
	}

	@DisplayName("findByIdOrElseThrow - 성공")
	@Test
	void findByIdOrElseThrow_success() {
		// given
		User user = new User();

		// when
		when(userRepository.findById(any(UUID.class)))
			.thenReturn(Optional.of(user));

		User byId = userDomainService.findByIdOrElseThrow(UUID.randomUUID());

		// then
		assertThat(byId).isNotNull();
	}

}