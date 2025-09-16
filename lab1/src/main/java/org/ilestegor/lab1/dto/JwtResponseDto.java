package org.ilestegor.lab1.dto;

import java.time.LocalDateTime;

public record JwtResponseDto(
        LocalDateTime expirationDate,
        String tokenType,
        boolean isLoggedIn
) {
}
