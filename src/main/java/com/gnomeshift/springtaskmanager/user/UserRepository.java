package com.gnomeshift.springtaskmanager.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(@Email @NotNull String email);
    User findByEmail(@Email @NotNull String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.failedLoginAttempt = :failLoginAttempts WHERE u.email = :email")
    void updateFailedLoginAttempts(@Param("email") String email, @Param("failLoginAttempts") int failLoginAttempts);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lockTime = :lockTime WHERE u.email = :email")
    void lock(@Param("email") String email, @Param("lockTime") LocalDateTime lockTime);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lockTime = null, u.failedLoginAttempt = 0 WHERE u.email = :email")
    void unlock(@Param("email") String email);
}
