package com.ada.earthvalley.yomojomo.auth.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.auth.dtos.TempAuthLoginRequest;
import com.ada.earthvalley.yomojomo.auth.services.TempAuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/temp")
@RestController
public class TempAuthController {
	private final TempAuthService tempAuthService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody TempAuthLoginRequest loginRequest) {
		return ResponseEntity.ok(tempAuthService.login(loginRequest));
	}

	@PostMapping("/signup")
	public ResponseEntity signUp(@RequestBody TempAuthLoginRequest loginRequest) {
		tempAuthService.signUp(loginRequest);
		return ResponseEntity.created(URI.create("/users")).build();
	}

}
