package com.ada.earthvalley.yomojomo.activity.repositories;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.BaseDataJpaTest;
import com.ada.earthvalley.yomojomo.activity.entities.Activity;
import com.ada.earthvalley.yomojomo.activity.entities.enums.ActivityStatus;

class ActivityRepositoryTest extends BaseDataJpaTest {
	@Autowired
	ActivityRepository activityRepository;

	@DisplayName("existByArticleIdAndActivityStatus")
	@Test
	void existsByArticleIdAndStatus_성공() throws Exception {
		// given
		Constructor<Activity> constructor = Activity.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		Activity activity1 = constructor.newInstance();
		Activity activity2 = constructor.newInstance();
		ReflectionTestUtils.setField(activity1, "articleId", 1L);
		ReflectionTestUtils.setField(activity1, "status", ActivityStatus.FINISHED);
		ReflectionTestUtils.setField(activity2, "articleId", 2L);
		ReflectionTestUtils.setField(activity2, "status", ActivityStatus.IN_PROGRESS);

		em.persist(activity1);
		em.persist(activity2);
		flushAndClear();

		// when
		boolean bool1 = activityRepository.existsByArticleIdAndStatus(1L, ActivityStatus.FINISHED);
		boolean bool2 = activityRepository.existsByArticleIdAndStatus(2L, ActivityStatus.FINISHED);
		boolean bool3 = activityRepository.existsByArticleIdAndStatus(3L, ActivityStatus.FINISHED);

		// then
		assertThat(bool1).isTrue();
		assertThat(bool2).isFalse();
		assertThat(bool3).isFalse();
	}

}