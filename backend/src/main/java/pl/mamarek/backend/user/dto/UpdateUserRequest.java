package pl.mamarek.backend.user.dto;

import pl.mamarek.backend.user.model.UserRole;

public record UpdateUserRequest(
        String username,
        String email,
        String password,
        UserRole role,
        Boolean enabled
) {
}
