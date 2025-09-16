package org.ilestegor.lab1.dto;

import jakarta.validation.constraints.NotEmpty;

public record JwtRequestDto(
        @NotEmpty
        String username,
        @NotEmpty
        String password
) {
}
