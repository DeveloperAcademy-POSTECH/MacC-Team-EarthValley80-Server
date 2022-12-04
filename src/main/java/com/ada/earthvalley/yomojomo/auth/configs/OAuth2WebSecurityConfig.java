package com.ada.earthvalley.yomojomo.auth.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.ada.earthvalley.yomojomo.auth.oauth2.OAuth2AuthenticationEntryPoint;
import com.ada.earthvalley.yomojomo.auth.oauth2.services.YomojomoOAuth2UserService;

@EnableWebSecurity
public class OAuth2WebSecurityConfig {

	@Bean
	public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.antMatcher("/oauth2/**")
			.oauth2Login(oauth2 -> {
				oauth2
					.authorizationEndpoint(authorization -> {
						authorization.baseUri("/oauth2/authorization");
					})
					.userInfoEndpoint(userInfo -> {
						userInfo.userService(new YomojomoOAuth2UserService());
					});
			})
			.csrf(csrf -> {
				csrf.disable();
			})
			.exceptionHandling(exception -> {
				exception
					.authenticationEntryPoint(new OAuth2AuthenticationEntryPoint());
			})
			.authorizeRequests(c -> {
				c
					.anyRequest().authenticated();
			});
		return http.build();
	}

	@Bean
	public SecurityFilterChain oauth2RedirectFilterChain(HttpSecurity http) throws Exception {
		http
			.antMatcher("/login/oauth2/**")
			.oauth2Login(oauth2 -> {
			})
			.authorizeRequests()
			.anyRequest().authenticated();
		return http.build();
	}
}
