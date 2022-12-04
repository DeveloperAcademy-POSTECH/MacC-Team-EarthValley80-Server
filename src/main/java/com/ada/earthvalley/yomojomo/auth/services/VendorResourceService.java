package com.ada.earthvalley.yomojomo.auth.services;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.repositories.VendorResourceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VendorResourceService {
	private final VendorResourceRepository vendorResourceRepository;

	public VendorResource findVendorResourceOrThrow(YomojomoOAuth2User user) throws NoSuchElementException {
		return vendorResourceRepository.findByVendorIdAndType(user.getSocialId(),
			user.getVendorType()).orElseThrow(NoSuchElementException::new);
	}

	public void throwIfVendorResourceExist(YomojomoOAuth2User user) throws IllegalStateException {
		vendorResourceRepository.findByVendorIdAndType(
				user.getSocialId(), user.getVendorType())
			.ifPresent(v -> {
				throw new IllegalStateException();
			});
	}
}
