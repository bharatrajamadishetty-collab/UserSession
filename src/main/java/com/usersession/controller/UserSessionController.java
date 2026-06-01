package com.usersession.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usersession.entity.UserSession;
import com.usersession.exception.ResourceNotFoundException;
import com.usersession.service.UserSessionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1")
@CrossOrigin()
public class UserSessionController {
    private static final Logger log = LoggerFactory.getLogger(UserSessionController.class);

    private final UserSessionService userService;

    public UserSessionController(UserSessionService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public List<UserSession> searchUsers(@Valid @RequestParam String userName) {
        return userService.findByUsername(userName);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserSession> getUserById(@PathVariable Long id) {
        UserSession user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSession>> getUsers() {
        log.info("Get all users triggered");
        List<UserSession> users = userService.getUsers();
        if (users == null || users.isEmpty()) {
            throw new ResourceNotFoundException("Users not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-session")
    public ResponseEntity<String> deleteUsers(@Valid @RequestParam String expiresAt) {
        log.info("Delete User Sessions triggered");
        int count = userService.deletedExpiredSessions(expiresAt);
        if (count == 0) {
            throw new ResourceNotFoundException("No expired user sessions found to delete");
        }
        return ResponseEntity.status(HttpStatus.OK).body(count + "User Sessions deleted");

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserSession user) throws Exception {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

}
