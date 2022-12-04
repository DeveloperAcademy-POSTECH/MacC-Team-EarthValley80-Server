package com.ada.earthvalley.yomojomo.activity.entities;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ada.earthvalley.yomojomo.BaseDataJpaTest;
import com.ada.earthvalley.yomojomo.group.entities.GroupUser;
import com.ada.earthvalley.yomojomo.user.entities.User;

public class ReadingEntityTest extends BaseDataJpaTest {
	User user1;
	Reading reading1;
	Reading reading2;
	Reading reading3;

	@BeforeEach
	void init() throws Exception {
		user1 = new User();
		Constructor<Reading> readingConstructor = Reading.class.getDeclaredConstructor();
		readingConstructor.setAccessible(true);
		reading1 = readingConstructor.newInstance();
		reading2 = readingConstructor.newInstance();
		reading3 = readingConstructor.newInstance();
	}

	@DisplayName("User -> Reading - Cascade.PERSIST 성공")
	@Test
	void user_reading_cascade_persist() {
		// given
		user1.addReadings(List.of(reading1, reading2, reading3));

		// when
		em.persist(user1);
		flushAndClear();
		User findUser = em.find(User.class, user1.getId());

		// then
		assertThat(findUser).isNotNull();
		assertThat(findUser.getReadings()).hasSize(3);
		assertThat(em.find(Reading.class, reading1.getId())).isNotNull();
		assertThat(em.find(Reading.class, reading2.getId())).isNotNull();
		assertThat(em.find(Reading.class, reading3.getId())).isNotNull();
	}

	@DisplayName("User -> Reading - orphan removal - 성공")
	@Test
	void user_reading_orphan_removal() {
		// given1
		user1.addReadings(List.of(reading1, reading2, reading3));

		// when1
		em.persist(user1);
		flushAndClear();
		User findUser = em.find(User.class, user1.getId());

		// then 1
		assertThat(findUser.getReadings()).hasSize(3);

		findUser.getReadings().remove(1);
		// Reading reading = findUser.getReadings().get(1);
		// em.remove(reading);
		flushAndClear();

		User afterUser = em.find(User.class, user1.getId());
		assertThat(afterUser.getReadings()).hasSize(2);
		Reading findReading = em.find(Reading.class, reading2.getId());
		assertThat(findReading).isNull();
	}
}
