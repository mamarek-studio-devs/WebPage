package pl.mamarek.backend.auth.dto;

import pl.mamarek.backend.user.dto.UserDto;

public record AuthResponse(
        String token,
        String tokenType,
        UserDto user
) {
}
