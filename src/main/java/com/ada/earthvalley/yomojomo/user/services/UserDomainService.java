package com.ada.earthvalley.yomojomo.user.services;

import static com.ada.earthvalley.yomojomo.user.exceptions.UserError.*;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.repositories.VendorResourceRepository;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.ada.earthvalley.yomojomo.user.exceptions.YomojomoUserException;
import com.ada.earthvalley.yomojomo.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDomainService {
	private final UserRepository userRepository;
	private final VendorResourceRepository vendorResourceRepository;

	@Transactional
	public User createUserWithOAuth2(YomojomoOAuth2User user) {
		User savedUser = userRepository.save(new User());
		vendorResourceRepository.save(new VendorResource(user, savedUser));
		return savedUser;
	}

	public User findByIdOrElseThrow(UUID id) throws YomojomoUserException {
		return userRepository.findById(id)
			.orElseThrow(() -> new YomojomoUserException(USER_NOT_FOUND));
	}

	public User findByVendorResource(VendorResource vendorResource) {
		return vendorResource.getUser();
	}
}
