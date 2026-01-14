package com.auth.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auth.backend.entity.User;
import com.auth.backend.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        log.info("Get user by email : {}", email);
        return userRepository.findByEmail(email).get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
