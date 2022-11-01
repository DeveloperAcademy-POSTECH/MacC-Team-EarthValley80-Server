package com.ada.earthvalley.yomojomo.auth;

import com.ada.earthvalley.yomojomo.auth.jwt.BearerAuthenticationConverter;
import com.ada.earthvalley.yomojomo.auth.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final BearerAuthenticationConverter converter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtAuthenticationToken convert = converter.convert(request);


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return false;
    }
}
