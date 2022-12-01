package com.ada.earthvalley.yomojomo.auth.api;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.auth.dtos.LoginResponse;
import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.YomojomoUser;
import com.ada.earthvalley.yomojomo.auth.services.OAuth2ApiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
@RestController
public class OAuth2Controller {
	private final OAuth2ApiService authApiService;

	@GetMapping("/login")
	public ResponseEntity<LoginResponse> login(@YomojomoUser YomojomoOAuth2User user) {
		try {
			return ResponseEntity.ok(authApiService.oauth2LoginOrSignUp(user));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/api/oauth2/signup").build();
		}
	}

	@GetMapping("/signup")
	public ResponseEntity<LoginResponse> signup(@YomojomoUser YomojomoOAuth2User user) {
		return ResponseEntity.ok(authApiService.oauth2SignUp(user));
	}
}
