package com.ada.earthvalley.yomojomo.auth.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ada.earthvalley.yomojomo.auth.jwt.services.TokenTimeUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "token_id")
	private Long id;

	private String refreshToken;

	private LocalDateTime expiredAt = TokenTimeUtils.getRefreshTokenLocalDateTime();

	private LocalDateTime issuedAt = LocalDateTime.now();

	public RefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
