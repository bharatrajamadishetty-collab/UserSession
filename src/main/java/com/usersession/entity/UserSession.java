package com.usersession.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long userId;

    @NotBlank(message = "userName cannot be blank")
    @Size(min = 4, max = 20, message = "username must be between 4 and 20 characters")
    @Column
    String username;

    @NotBlank(message = "expiresAt cannot be blank")
    @Column
    String expiresAt;
}
