package com.ada.earthvalley.yomojomo.common;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.common.propertyServices.JwtPropertyService;

@DisplayName("@Value를 이용한 property 주입 테스트")
@SpringBootTest
public class PropertyInjectionTest {
	@Autowired
	private JwtPropertyService jwtPropertyService;

	// TODO: @Value 가 main path의 값을 가져오도록 테스트 (by Leo - 22.11.15)
	@DisplayName("JwtSecretsService 프로퍼티 테스트")
	@Test
	void jwtSecrets() {
		String secret = (String)ReflectionTestUtils.getField(jwtPropertyService, "secret");
		String issuer = jwtPropertyService.getIssuer();

		assertThat(jwtPropertyService).isNotNull();
		assertThat(issuer).isNotNull();
		assertThat(secret).isNotNull();
	}
}
