package com.ada.earthvalley.yomojomo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Disabled
@DataJpaTest
public class BaseDataJpaTest {
	@PersistenceContext
	protected EntityManager em;

	protected void flushAndClear() {
		em.flush();
		em.clear();
	}
}
