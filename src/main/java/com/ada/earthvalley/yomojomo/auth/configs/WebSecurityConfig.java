package com.ada.earthvalley.yomojomo.auth.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ada.earthvalley.yomojomo.auth.jwt.JwtAuthenticationFilter;
import com.ada.earthvalley.yomojomo.auth.jwt.BearerAuthenticationConverter;
import com.ada.earthvalley.yomojomo.auth.jwt.services.JwtUtilsService;

@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private JwtUtilsService jwtUtilsService;

	private JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(authenticationConverter(), jwtUtilsService);
	}

	private BearerAuthenticationConverter authenticationConverter() {
		return new BearerAuthenticationConverter();
	}

	@Bean
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.addFilterAt(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
			.antMatcher("/api/**")
			.authorizeRequests(c -> {
				c.anyRequest().authenticated();
			});
		return http.build();
	}
}
