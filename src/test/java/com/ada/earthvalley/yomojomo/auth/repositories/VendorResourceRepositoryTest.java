package com.ada.earthvalley.yomojomo.auth.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.entities.enums.VendorType;

@DataJpaTest
class VendorResourceRepositoryTest {
	@Autowired
	VendorResourceRepository vendorResourceRepository;

	@PersistenceContext
	EntityManager em;

	@BeforeEach
	void init() {

	}

	@DisplayName("socialId, vendorType으로 찾기 - 실패 (없음)")
	@Test
	void findOptionalByVendorIdAndType_fail_empty() throws Exception {
		// when
		Optional<VendorResource> vendorResource = vendorResourceRepository.findByVendorIdAndType("anyString",
			VendorType.KAKAO);

		// then
		assertThat(vendorResource).isEmpty();
	}

	@DisplayName("socialId, vendorType으로 찾기 - 성공")
	@Test
	void findOptionalByVendorIdAndType_success() throws Exception {
		// given
		final String vendorId = "123456";
		final VendorType type = VendorType.KAKAO;
		Constructor<VendorResource> constructor = VendorResource.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		VendorResource vendorResource = constructor.newInstance();
		ReflectionTestUtils.setField(vendorResource, "vendorId", vendorId);
		ReflectionTestUtils.setField(vendorResource, "type", type);

		em.persist(vendorResource);
		flushAndClear();

		// when
		Optional<VendorResource> find = vendorResourceRepository.findByVendorIdAndType(
			vendorId, type);

		// then
		assertThat(find).isNotEmpty();
		assertThat(find.orElseThrow(IllegalArgumentException::new).getVendorId()).isEqualTo(vendorId);
		assertThat(find.orElseThrow(IllegalArgumentException::new).getType()).isEqualTo(type);
	}

	void flushAndClear() {
		em.flush();
		em.clear();
	}
}