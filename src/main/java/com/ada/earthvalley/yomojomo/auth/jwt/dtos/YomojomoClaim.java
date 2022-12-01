package com.ada.earthvalley.yomojomo.auth.jwt.dtos;

import java.util.UUID;

import com.ada.earthvalley.yomojomo.user.entities.User;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class YomojomoClaim {
	private UUID userId;

	public String getUserId() {
		return userId.toString();
	}

	public static YomojomoClaim of(Claims claims) {
		return new YomojomoClaim(UUID.fromString(claims.getSubject()));
	}

	public static YomojomoClaim ofUser(User user) {
		return new YomojomoClaim(user.getId());
	}
}
