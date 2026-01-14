package com.auth.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.backend.dto.AuthResponse;
import com.auth.backend.dto.LoginRequest;
import com.auth.backend.dto.RegisterRequest;
import com.auth.backend.dto.UserResponse;
import com.auth.backend.entity.AuthProvider;
import com.auth.backend.entity.RefreshToken;
import com.auth.backend.entity.Role;
import com.auth.backend.entity.User;
import com.auth.backend.repository.UserRepository;
import com.auth.backend.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private AuthenticationManager authenticationManager;
    private CookieUtil cookieUtil;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService,
            RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager,
            CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.cookieUtil = cookieUtil;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request, HttpServletResponse response) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .provider(AuthProvider.LOCAL)
                .role(Role.USER)
                .enabled(true)
                .build();

        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                request.getPassword());

        String accessToken = jwtService.generateAccessToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        cookieUtil.addCookie(response, "access_token", accessToken, (int) accessTokenExpiration);
        cookieUtil.addCookie(response, "refresh_token", refreshToken.getToken(), (int) refreshTokenExpiration);

        return AuthResponse.builder()
                .success(true)
                .message("Registration successfull")
                .user(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getFirstName() + user.getLastName())
                        .role(user.getRole().name())
                        .build())
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request, HttpServletResponse response) {
        log.info("Login for email {}", request.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        log.info("Finish authenctication {}", authentication.getName());
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtService.generateAccessToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        log.info("Finish create token");

        cookieUtil.addCookie(response, "access_token", accessToken, (int) accessTokenExpiration);
        cookieUtil.addCookie(response, "refresh_token", refreshToken.getToken(), (int) refreshTokenExpiration);
        log.info("Finish add cookie");

        return AuthResponse.builder()
                .success(true)
                .message("Login successfull")
                .user(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getFirstName() + " " + user.getLastName())
                        .role(user.getRole().name())
                        .build())
                .build();
    }

    @Transactional
    public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshTokenValue = cookieUtil.getCookie(request, "refresh_token")
                .orElseThrow(() -> new RuntimeException("Refresh token is not found"));

        return refreshTokenService.findByToken(refreshTokenValue)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String access_token = jwtService.generateAccessToken(
                            user.getEmail(),
                            "ROLE_" + user.getRole().name());

                    cookieUtil.addCookie(response, "access_token", access_token,
                            (int) accessTokenExpiration);
                    return AuthResponse.builder()
                            .success(true)
                            .message("Token refreshed successfully")
                            .user(UserResponse.builder()
                                    .id(user.getId())
                                    .email(user.getEmail())
                                    .name(user.getFirstName() + " "
                                            + user.getLastName())
                                    .role(user.getRole().name())
                                    .build())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    @Transactional
    public void logout(String email, HttpServletResponse response) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenService.deleteByUser(user);
        cookieUtil.deleteCookie(response, "access_token");
        cookieUtil.deleteCookie(response, "refresh_token");
    }
}
