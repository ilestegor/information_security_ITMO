package org.ilestegor.lab1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.ilestegor.lab1.model.Priority;

import java.time.Instant;
import java.time.LocalDateTime;

public record ResponseTodoDto(
        Long id,
        String taskName,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime deadline,
        Priority priority,
        Instant created,
        boolean isCompleted
) {
}
