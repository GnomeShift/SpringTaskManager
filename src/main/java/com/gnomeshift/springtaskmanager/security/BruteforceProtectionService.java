package com.gnomeshift.springtaskmanager.security;

import com.gnomeshift.springtaskmanager.user.User;
import com.gnomeshift.springtaskmanager.user.UserRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class BruteforceProtectionService {
    private final int MAX_FAILED_LOGIN_ATTEMPTS = 5;
    private final long LOCK_TIME = TimeUnit.MINUTES.toMinutes(1);

    @Autowired
    private UserRepository userRepository;

    public void registerFailedLogin(@Email String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return;
        }

        int failedLoginAttempts = user.getFailedLoginAttempt() + 1;
        userRepository.updateFailedLoginAttempts(email, failedLoginAttempts);

        if (failedLoginAttempts >= MAX_FAILED_LOGIN_ATTEMPTS) {
            lockUser(email);
        }
    }

    public void resetFailedLoginAttempts(@Email String email) {
        userRepository.unlock(email);
    }

    public boolean isLocked(@Email String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        LocalDateTime lockTime = user.getLockTime();
        return lockTime != null && lockTime.isAfter(LocalDateTime.now());
    }

    public void lockUser(@Email String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return;
        }

        LocalDateTime lockUntil = LocalDateTime.now().plusMinutes(LOCK_TIME);
        userRepository.lock(email, lockUntil);
    }

    public long getLockTime(@Email String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return 0;
        }

        LocalDateTime lockTime = user.getLockTime();

        if (lockTime != null && lockTime.isAfter(LocalDateTime.now())) {
            return Duration.between(LocalDateTime.now(), lockTime).toMinutes();
        }

        return 0;
    }
}
