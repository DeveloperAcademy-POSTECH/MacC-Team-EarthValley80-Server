package com.ada.earthvalley.yomojomo.auth.entities;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class RefreshToken {
	private String refreshToken;
	private LocalDateTime expiredAt;
	private LocalDateTime issuedAt = LocalDateTime.now();
}
