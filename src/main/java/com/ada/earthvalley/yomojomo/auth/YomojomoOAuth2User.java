package com.ada.earthvalley.yomojomo.auth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.ada.earthvalley.yomojomo.auth.entities.enums.VendorType;

import lombok.Getter;
import lombok.Setter;

@Getter
public class YomojomoOAuth2User implements OAuth2User {
	private Map<String, Object> attributes;
	private Collection<? extends GrantedAuthority> authorities;
	private String name;
	@Setter
	private VendorType vendorType;

	public YomojomoOAuth2User(OAuth2User oAuth2User) {
		attributes = oAuth2User.getAttributes();
		authorities = oAuth2User.getAuthorities();
		name = oAuth2User.getName();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getSocialId() {
		return getName();
	}
}
