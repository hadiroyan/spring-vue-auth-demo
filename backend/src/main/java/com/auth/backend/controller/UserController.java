package com.auth.backend.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.backend.dto.UserDto;
import com.auth.backend.entity.AuthProvider;
import com.auth.backend.entity.Role;
import com.auth.backend.entity.User;
import com.auth.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        User userRequest = User.builder()
                .provider(AuthProvider.LOCAL)
                .enabled(true)
                .role(Role.USER)
                .build();
                
        BeanUtils.copyProperties(userDto, userRequest);
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> read(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public ResponseEntity<List<User>> readAll() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> update(@RequestBody String email, @PathVariable User updateUser) {
        User user = userService.updateUserByEmail(email, updateUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        String emailDeleted = userService.deleteUserByEmail(email);
        return ResponseEntity.ok(emailDeleted);
    }
}
