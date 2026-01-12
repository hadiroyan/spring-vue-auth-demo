package com.auth.backend.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

    public User createUser(User user) {
        log.info("create user: {}", user);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        log.info("Get user by email : {}", email);
        return userRepository.findByEmail(email).get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUserByEmail(String email, User newUser) {
        log.info("Update user by email {}", email);
        User user = userRepository.findByEmail(email).get();
        BeanUtils.copyProperties(newUser, user);
        return userRepository.save(user);
    }

    public String deleteUserByEmail(String email) {
        log.info("Delete user by email {}", email);
        User user = userRepository.findByEmail(email).get();
        userRepository.delete(user);
        return email;
    }
}
