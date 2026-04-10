package com.usersession.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
public record UserSession(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column Long userId,
        @Column Timestamp expiresAt) {}
/* public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long userId;

    @Column
    Timestamp expiresAt;

    public UserSession(Timestamp expiresAt, Long userId) {
        this.expiresAt = expiresAt;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

} */
