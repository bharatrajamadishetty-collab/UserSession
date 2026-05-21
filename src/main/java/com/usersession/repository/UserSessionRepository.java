package com.usersession.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usersession.entity.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    @Modifying
    @Query("DELETE FROM UserSession s WHERE s.expiresAt <= :timestamp")
    int deleteExpiredSessions(@Param("timestamp") String timestamp);

}
