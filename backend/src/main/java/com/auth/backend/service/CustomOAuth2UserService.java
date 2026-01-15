package com.auth.backend.service;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.auth.backend.entity.AuthProvider;
import com.auth.backend.entity.Role;
import com.auth.backend.entity.User;
import com.auth.backend.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        log.info("Google OAuth2 login: {}; {}; {}", name, email, registrationId);

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            log.info("User from OAuth is present");
            user = userOptional.get();
            user.setFirstName(name);
            user.setProviderId(providerId);

        } else {
            log.info("User from OAuth is not present");
            user = User.builder()
                    .firstName(name)
                    .email(email)
                    .provider(AuthProvider.GOOGLE)
                    .providerId(providerId)
                    .role(Role.USER)
                    .enabled(true)
                    .build();
        }
        log.info("User to save from OAuth: {}", user);
        userRepository.save(user);
        log.info("SUCCESS process user from OAuth: {}", user);

        return oAuth2User;
    }
}
