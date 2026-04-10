package com.usersession.utils;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.usersession.service.UserSessionService;

@Component
@EnableScheduling
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private final UserSessionService userSession;

    public ScheduledTask(UserSessionService userSession) {
        this.userSession = userSession;
    }

    @Scheduled(cron = "*/30 * * * * *") // every 30 seconds
    public synchronized void userSessionExpiry() {
        try {
            log.info("Deleting User Sessions started");
            CompletableFuture<Integer> deletedUsers = CompletableFuture
                    .supplyAsync(() -> userSession.deletedExpiredSessions());
            if (deletedUsers.isDone()) {
                log.info("{} User Sessions deleted successfully", deletedUsers);
            } else {
                log.info("{} Failed to delete user sessions", deletedUsers);
            }
        } catch (Exception e) {
            log.error("Exception occured while deleting user sessions", e);
        }
    }

}
