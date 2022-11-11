package com.ada.earthvalley.yomojomo.common.baseEntities;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.word.entities.Word;

@Transactional
@SpringBootTest
public class BaseEntityTest {
	@PersistenceContext
	private EntityManager em;

	@DisplayName("BaseEntity - 성공")
	@Test
	void baseentity_success() throws
		NoSuchMethodException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		// given
		Constructor<Word> constructor = Word.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		Word word = constructor.newInstance();

		// when
		em.persist(word);
		em.flush();
		em.clear();
		Word findWord = em.find(Word.class, word.getId());

		// then
		assertThat(findWord).isNotNull();
		assertThat(findWord.getCreatedAt()).isNotNull();
		assertThat(findWord.getUpdatedAt()).isNotNull();
	}
}
