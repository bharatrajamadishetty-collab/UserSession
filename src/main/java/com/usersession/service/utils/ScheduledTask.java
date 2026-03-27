package com.usersession.service.utils;

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

    @Scheduled(cron = "0 0 * * * *") // every hour
    public synchronized void userSessionExpiry() {
        try {
            log.info("Deleting User Sessions started");
            CompletableFuture<Integer> deletedUsers = CompletableFuture
                    .supplyAsync(() -> userSession.deletedExpiredSessions());
            log.info("{} User Sessions deleted successfully", deletedUsers);
        } catch (Exception e) {
            log.error("Deleting User Sessions failed", e);
        }
    }

}
