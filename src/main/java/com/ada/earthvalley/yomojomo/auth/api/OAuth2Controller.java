package com.ada.earthvalley.yomojomo.auth.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;
import com.ada.earthvalley.yomojomo.auth.YomojomoUser;
import com.ada.earthvalley.yomojomo.auth.dtos.LoginResponse;
import com.ada.earthvalley.yomojomo.auth.services.OAuth2ApiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/oauth2")
@RestController
public class OAuth2Controller {
	private final OAuth2ApiService authApiService;

	@GetMapping("/login")
	public ResponseEntity<LoginResponse> login(@YomojomoUser YomojomoOAuth2User user) {
		return ResponseEntity.ok(authApiService.oauth2Login(user));
	}

	@PostMapping("/signup")
	public ResponseEntity signup(@YomojomoUser YomojomoOAuth2User user) {
		String userId = authApiService.oauth2SignUp(user);
		return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
	}
}
