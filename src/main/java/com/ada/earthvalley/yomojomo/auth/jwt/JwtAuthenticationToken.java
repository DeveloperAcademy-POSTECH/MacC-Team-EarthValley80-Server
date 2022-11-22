package com.ada.earthvalley.yomojomo.auth.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;

import java.util.Collection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// TODO: Builder 클래스 생성 (by Leo - 22.10.30)
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtAuthenticationToken implements Authentication {
	private SecurityUser user;
	private boolean authenticated = false;
	private String credentials;

	public static JwtAuthenticationToken unauthenticated(String accessToken) {
		return new JwtAuthenticationToken(accessToken);
	}

	public void setClaims(YomojomoClaim claim) {
		this.authenticated = true;
		user = SecurityUser.of(claim);
	}

	private JwtAuthenticationToken(String accessToken) {
		this.credentials = accessToken;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public String getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public SecurityUser getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
	}

	@Override
	public String getName() {
		return null;
	}
}
