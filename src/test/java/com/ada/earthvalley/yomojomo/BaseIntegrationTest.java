package com.ada.earthvalley.yomojomo;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;
import com.ada.earthvalley.yomojomo.auth.jwt.services.JwtUtilsService;
import com.ada.earthvalley.yomojomo.user.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class BaseIntegrationTest {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected EntityManager em;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected ApplicationContext applicationContext;

	protected User testUser;
	protected String testUserAccessToken;

	@BeforeEach
	void init_test_user() {
		testUser = new User();
		em.persist(testUser);
		String accessToken = applicationContext.getBean(JwtUtilsService.class)
			.createAccessToken(YomojomoClaim.ofUser(testUser));
		testUserAccessToken = "Bearer " + accessToken;
		flushAndClear();
	}

	protected void flushAndClear() {
		em.flush();
		em.clear();
	}
}
