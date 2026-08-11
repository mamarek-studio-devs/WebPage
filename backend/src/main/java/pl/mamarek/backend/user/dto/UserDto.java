package pl.mamarek.backend.user.dto;

import pl.mamarek.backend.user.model.UserRole;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String username,
        String email,
        UserRole role,
        boolean enabled,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
