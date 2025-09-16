package org.ilestegor.lab1.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record JwtRequestDto(
        @NotEmpty
        @NotNull
        String username,
        @NotEmpty
        @NotNull
        String password
) {
}
