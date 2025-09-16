package org.ilestegor.lab1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.ilestegor.lab1.model.Priority;

import java.time.LocalDateTime;

public record RequestTodoDto(
        @NotEmpty
        String taskName,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @NotNull
        LocalDateTime deadline,
        String description,
        @NotNull
        Priority priority
) {
}