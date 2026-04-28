package com.usersession.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usersession.entity.UserSession;
import com.usersession.service.UserSessionService;

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
    public List<UserSession> searchUsers(@RequestParam String userName) {
        return userService.findByUsername(userName);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSession>> getUsers() {
        log.info("Get all users triggered");
        try {
            List<UserSession> users = userService.getUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            log.error("Failed to get all Users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/delete-session")
    public ResponseEntity<String> deleteUsers() {
        log.info("Delete User Sessions triggered");
        try {
            int count = userService.deletedExpiredSessions();
            return ResponseEntity.status(HttpStatus.OK).body(count + "User Sessions deleted");
        } catch (Exception e) {
            log.error("Deleting User Sessions failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user sessions");
        }

    }

}
