package org.ilestegor.lab1.exception.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountLockedException extends RuntimeException {

    private final LocalDateTime unlockAt;

    public AccountLockedException(LocalDateTime unlockAt) {
        this.unlockAt = unlockAt;
    }

    public String getUnlockAt() {
        return unlockAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
