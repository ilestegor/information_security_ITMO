package org.ilestegor.lab1.service.impl;

import jakarta.transaction.Transactional;
import org.ilestegor.lab1.model.User;
import org.ilestegor.lab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class LoginAttemptService {
    private final UserRepository users;

    @Value("${login.policy.max_failed_attempts}")
    private int MAX_FAILED_ATTEMPTS;

    @Value("${login.policy.lock_duration}")
    private Duration LOCK_DURATION;

    public LoginAttemptService(UserRepository users) {
        this.users = users;
    }

    public void increaseFailedAttempts(String username) {
        users.findByUserName(username).ifPresent(u -> {
            if (!u.isAccountNonLocked() && !unlockIfExpired(u)) return;

            u.setFailedAttempts(u.getFailedAttempts() + 1);
            if (u.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
                lock(u);
            }
            users.save(u);
        });
    }

    public void resetFailedAttempts(String username) {
        users.findByUserName(username).ifPresent(u -> {
            u.setFailedAttempts(0);
            u.setAccountNonLocked(true);
            u.setLockTime(null);
            users.save(u);
        });
    }

    public boolean unlockIfExpired(User u) {
        if (!u.isAccountNonLocked() && u.getLockTime() != null) {
            if (u.getLockTime().plus(LOCK_DURATION).isBefore(LocalDateTime.now())) {
                u.setAccountNonLocked(true);
                u.setFailedAttempts(0);
                u.setLockTime(null);
                return true;
            }
        }
        return false;
    }

    private void lock(User u) {
        u.setAccountNonLocked(false);
        u.setLockTime(LocalDateTime.now());
    }

    public Optional<LocalDateTime> getUnlockAt(String username) {
        return users.findByUserName(username)
                .filter(u -> !u.isAccountNonLocked() && u.getLockTime() != null)
                .map(u -> u.getLockTime().plus(LOCK_DURATION));
    }
}