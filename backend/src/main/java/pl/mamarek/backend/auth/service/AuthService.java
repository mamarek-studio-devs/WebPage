package pl.mamarek.backend.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.mamarek.backend.auth.dto.AuthResponse;
import pl.mamarek.backend.auth.dto.LoginRequest;
import pl.mamarek.backend.auth.dto.MessageResponse;
import pl.mamarek.backend.auth.dto.RegisterRequest;
import pl.mamarek.backend.exception.ResourceNotFoundException;
import pl.mamarek.backend.auth.security.JwtService;
import pl.mamarek.backend.user.dto.UserDto;
import pl.mamarek.backend.user.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        UserDto user = userService.register(request);
        return new AuthResponse(jwtService.generateToken(user), "Bearer", user);
    }
    //let write empty contructor


    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDto userDto = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new AuthResponse(jwtService.generateToken(userDto), "Bearer", userDto);
    }

    public MessageResponse logout() {
        return new MessageResponse("Logged out successfully");
    }
}
