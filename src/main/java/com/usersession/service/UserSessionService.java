package com.usersession.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usersession.repository.UserSessionRepository;

@Service
public class UserSessionService {
    private static final Logger log = LoggerFactory.getLogger(UserSessionService.class);

    private final UserSessionRepository usrepo;

    public UserSessionService(UserSessionRepository usrepo) {
        this.usrepo = usrepo;
    }

    @Transactional
    public int deletedExpiredSessions() {
        Instant now = Instant.now();
        int count = usrepo.deleteExpiredSessions(now);
        log.info("Deleted {} expired user sessions", count);
        return count;
    }

}
