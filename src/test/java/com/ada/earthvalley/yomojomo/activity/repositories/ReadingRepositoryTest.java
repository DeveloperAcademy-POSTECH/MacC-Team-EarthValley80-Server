package com.ada.earthvalley.yomojomo.activity.repositories;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ada.earthvalley.yomojomo.BaseDataJpaTest;
import com.ada.earthvalley.yomojomo.activity.entities.Reading;
import com.ada.earthvalley.yomojomo.user.entities.User;

class ReadingRepositoryTest extends BaseDataJpaTest {
	@Autowired
	ReadingRepository readingRepository;

	@DisplayName("findByUserId - 성공")
	@Test
	void findByUserId_성공() throws Exception {
		// given
		User user = new User();
		Constructor<Reading> constructor = Reading.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		Reading reading1 = constructor.newInstance();
		Reading reading2 = constructor.newInstance();
		user.addReadings(List.of(reading1, reading2));
		em.persist(user);
		flushAndClear();

		// when
		List<Reading> result = readingRepository.findByUserId(user.getId());

		// then
		assertThat(result).hasSize(2);
	}
}