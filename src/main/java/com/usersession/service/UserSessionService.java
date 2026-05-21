package com.usersession.service;

import java.util.ArrayList;
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

    public UserSessionService(UserSessionRepository usrepo, @Lazy ScheduledTask task) { // Lazy loading to avoid
                                                                                        // circular reference
        this.usrepo = usrepo;
        this.task = task;
    }

    public List<UserSession> getUsers() {
        List<UserSession> users = new ArrayList<>();
        users.add(new UserSession(Long.valueOf(11), "Suma", "2600ms"));
        users.add(new UserSession(Long.valueOf(12), "Neyansh", "3600ms"));
        users.add(new UserSession(Long.valueOf(13), "Bharat", "6500ms"));
        users.addAll(usrepo.findAll());
        return users;
    }

    public void executeTask() {
        task.userSessionExpiry();
    }

    @Transactional
    public int deletedExpiredSessions(String expiresAt) {
        int count = usrepo.deleteExpiredSessions(expiresAt);
        log.info("Deleted {} expired user sessions", count);
        return count;
    }

    public List<UserSession> findByUsername(String userName) {
        List<UserSession> userDetails = getUsers();
        return userDetails.stream()
                .filter(user -> user.getUserName().equalsIgnoreCase(userName))
                .toList();

    }

    @Transactional
    public void createUser(UserSession user) throws Exception {
        usrepo.save(user);
    }

}
