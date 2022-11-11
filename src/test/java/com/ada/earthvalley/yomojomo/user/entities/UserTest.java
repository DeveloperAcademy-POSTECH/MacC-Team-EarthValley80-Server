package com.ada.earthvalley.yomojomo.user.entities;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserTest {
	@PersistenceContext
	private EntityManager em;

	@DisplayName("User find - 성공")
	@Test
	void user_find_success() throws
		NoSuchMethodException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		// given
		Constructor<User> constructor = User.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		User user = constructor.newInstance();

		// when
		em.persist(user);
		em.flush();
		em.clear();
		User findUser = em.find(User.class, user.getId());

		// then
		assertThat(findUser).isNotNull();
	}
}
