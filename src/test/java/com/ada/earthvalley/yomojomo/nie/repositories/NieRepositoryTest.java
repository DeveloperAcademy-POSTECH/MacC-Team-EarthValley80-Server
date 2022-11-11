package com.ada.earthvalley.yomojomo.nie.repositories;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ada.earthvalley.yomojomo.nie.entities.Nie;

@DataJpaTest
class NieRepositoryTest {
	@Autowired
	NieRepository nieRepository;
	@PersistenceContext
	EntityManager em;

	Nie nie1;
	Nie nie2;
	Nie nie3;
	Nie nie4;

	@BeforeEach
	void initEach() throws
		NoSuchMethodException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		// TODO: Nie Builder 설정 시 refactoring (by Leo - 22.11.04)
		Constructor<Nie> nieConstructor = Nie.class.getDeclaredConstructor();
		nieConstructor.setAccessible(true);
		nie1 = nieConstructor.newInstance();
		nie2 = nieConstructor.newInstance();
		nie3 = nieConstructor.newInstance();
		nie4 = nieConstructor.newInstance();

		for (Nie nie : Arrays.asList(nie1, nie2, nie3, nie4)) {
			em.persist(nie);
		}
		em.flush();
		em.clear();
	}

	@DisplayName("findAll - 성공")
	@Test
	void find_all_success() {
		// when
		List<Nie> all = nieRepository.findAll();

		// then
		assertThat(all).hasSize(4);
	}

	@DisplayName("findById - 성공")
	@Test
	void find_by_id_success() {
		// when
		Optional<Nie> nie = nieRepository.findById(nie1.getId());

		// then
		assertThat(nie).isNotEmpty();
	}
}