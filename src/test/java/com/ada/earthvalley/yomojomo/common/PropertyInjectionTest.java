package com.ada.earthvalley.yomojomo.common;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.common.propertyServices.JwtPropertyService;

@DisplayName("@Value를 이용한 property 주입 테스트")
@SpringBootTest
public class PropertyInjectionTest {
	@Autowired
	private JwtPropertyService jwtPropertyService;

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoClientId;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoClientSecret;

	// TODO: @Value 가 main path의 값을 가져오도록 테스트 (by Leo - 22.11.15)
	@DisplayName("JwtSecretsService 프로퍼티 테스트")
	@Test
	void jwtSecrets() {
		String secret = (String)ReflectionTestUtils.getField(jwtPropertyService, "secret");
		String issuer = jwtPropertyService.getIssuer();

		System.out.println("secret = " + secret);
		System.out.println("issuer = " + issuer);

		assertThat(jwtPropertyService).isNotNull();
		assertThat(issuer).isNotNull();
		assertThat(secret).isNotNull();
		assertThat(issuer).isNotEqualTo("${JWT_CLAIMS_ISSUER}");
		assertThat(secret).isNotEqualTo("${JWT_SECRET_KEY}");
	}

	@DisplayName("OAuth2 ClientRegistration - KAKAO 테스트")
	@Test
	void kakaoClients() {
		System.out.println("kakaoClientId = " + kakaoClientId);
		System.out.println("kakaoClientSecret = " + kakaoClientSecret);

		assertThat(kakaoClientId).isNotNull();
		assertThat(kakaoClientSecret).isNotNull();
		assertThat(kakaoClientId).isNotEqualTo("${SPRING_SECURITY_KAKAO_CLIENT_ID}");
		assertThat(kakaoClientSecret).isNotEqualTo("${SPRING_SECURITY_KAKAO_CLIENT_SECRET}");
	}
}
