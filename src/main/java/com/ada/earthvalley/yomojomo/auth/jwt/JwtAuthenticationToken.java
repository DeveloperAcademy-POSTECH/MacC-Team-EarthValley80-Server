package com.ada.earthvalley.yomojomo.auth.jwt;

import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

// TODO: Builder 클래스 생성 (by Leo - 22.10.30)
@Component
public class JwtAuthenticationToken implements Authentication {
    private SecurityUser user;
    private boolean authenticated = false;
    private String credentials;

    public static JwtAuthenticationToken unauthenticated(String accessToken) {
        return new JwtAuthenticationToken(accessToken);
    }

    private JwtAuthenticationToken() {}
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

    public void setAuthenticated() {
        this.authenticated = authenticated;
    }
}
