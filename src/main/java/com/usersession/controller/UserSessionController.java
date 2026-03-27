package com.usersession.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.usersession.service.UserSessionService;

@RestController
@RequestMapping("/v1")
public class UserSessionController {
    private static final Logger log = LoggerFactory.getLogger(UserSessionController.class);

    private final UserSessionService userService;

    public UserSessionController(UserSessionService userService) {
        this.userService = userService;
    }

    @PostMapping("/delete-session")
    public ResponseEntity<String> postMethodName() {
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
