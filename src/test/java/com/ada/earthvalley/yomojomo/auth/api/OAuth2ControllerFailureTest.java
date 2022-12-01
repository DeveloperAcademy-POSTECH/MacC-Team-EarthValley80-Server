package com.ada.earthvalley.yomojomo.auth.api;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ada.earthvalley.yomojomo.auth.configs.OAuth2WebSecurityConfig;
import com.ada.earthvalley.yomojomo.auth.services.OAuth2ApiService;

@ContextConfiguration(
	classes = {
		OAuth2WebSecurityConfig.class,
		OAuth2Controller.class
	},
	initializers = ConfigDataApplicationContextInitializer.class
)
@WebAppConfiguration
@WebMvcTest
public class OAuth2ControllerFailureTest {
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private OAuth2Controller controller;
	@MockBean
	private OAuth2ApiService oAuth2ApiService;
	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();

	}
}