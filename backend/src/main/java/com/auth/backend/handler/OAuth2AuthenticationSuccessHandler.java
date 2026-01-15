package com.auth.backend.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.auth.backend.entity.User;
import com.auth.backend.repository.UserRepository;
import com.auth.backend.service.JwtService;
import com.auth.backend.service.RefreshTokenService;
import com.auth.backend.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public OAuth2AuthenticationSuccessHandler(JwtService jwtService, RefreshTokenService refreshTokenService,
            UserRepository userRepository, CookieUtil cookieUtil) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        log.info("On Authentication OAuth2 with email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtService.generateAccessToken(
                email,
                "ROLE_" + user.getRole().name());

        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        cookieUtil.addCookie(response, "access_token", accessToken, (int) accessTokenExpiration);
        cookieUtil.addCookie(response, "refresh_token", refreshToken, (int) refreshTokenExpiration);

        log.info("SUCCESS on authentication OAuth2 with email: {} . Redirect to front end", email);
        // Redirect to Vue.js
        response.sendRedirect(frontendUrl + "/auth/callback");
    }

}
