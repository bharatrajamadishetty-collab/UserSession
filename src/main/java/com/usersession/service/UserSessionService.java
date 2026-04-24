package com.usersession.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usersession.entity.UserSession;
import com.usersession.repository.UserSessionRepository;
import com.usersession.utils.ScheduledTask;

@Service
public class UserSessionService {
    private static final Logger log = LoggerFactory.getLogger(UserSessionService.class);

    private final UserSessionRepository usrepo;

    private final ScheduledTask task;

    public UserSessionService(UserSessionRepository usrepo, @Lazy ScheduledTask task) { // Lazy loading to avoid circular reference
        this.usrepo = usrepo;
        this.task = task;
    }

    public List<UserSession> getUsers() {
        List<UserSession> users;
        users = Arrays.asList(
                new UserSession(Long.valueOf(1), "Abhisheik", new Timestamp(3600)),
                new UserSession(Long.valueOf(2), "Charan", new Timestamp(1800)),
                new UserSession(Long.valueOf(3), "Tara", new Timestamp(4800)),
                new UserSession(Long.valueOf(4), "Bharath", new Timestamp(6100)),
                new UserSession(Long.valueOf(5), "Meena", new Timestamp(6900)),
                new UserSession(Long.valueOf(6), "Rajan", new Timestamp(8000)),
                new UserSession(Long.valueOf(7), "Prakruthi", new Timestamp(1300)),
                new UserSession(Long.valueOf(8), "Suma", new Timestamp(1600)),
                new UserSession(Long.valueOf(9), "Neyansh", new Timestamp(3000)),
                new UserSession(Long.valueOf(10), "Keerthi", new Timestamp(6000)));
        return users;
    }

    public void executeTask() {
        task.userSessionExpiry();
    }

    @Transactional
    public int deletedExpiredSessions() {
        Instant now = Instant.now();
        int count = usrepo.deleteExpiredSessions(now);
        log.info("Deleted {} expired user sessions", count);
        return count;
    }

    public List<UserSession> findByUsername(String userName) {
        List<UserSession> userDetails = getUsers();
        return userDetails.stream()
                .filter(user -> user.getUserName().equalsIgnoreCase(userName))
                .toList();

    }

}
