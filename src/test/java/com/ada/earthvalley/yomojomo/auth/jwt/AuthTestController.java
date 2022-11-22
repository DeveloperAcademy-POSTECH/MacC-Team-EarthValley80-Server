package com.ada.earthvalley.yomojomo.auth.jwt;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.auth.SecurityUser;

@RestController
public class AuthTestController {
	@GetMapping("/api/yomojomo-user")
	public String yomojomoUserTestRoute(
		@AuthenticationPrincipal SecurityUser user
	) {
		return user.getId().toString();
	}
}
