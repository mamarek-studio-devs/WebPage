package pl.mamarek.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mamarek.backend.auth.dto.RegisterRequest;
import pl.mamarek.backend.exception.ConflictException;
import pl.mamarek.backend.exception.ResourceNotFoundException;
import pl.mamarek.backend.user.dto.UpdateUserRequest;
import pl.mamarek.backend.user.dto.UserDto;
import pl.mamarek.backend.user.mapper.UserMapper;
import pl.mamarek.backend.user.model.User;
import pl.mamarek.backend.user.model.UserRole;
import pl.mamarek.backend.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public User requireUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    public UserDto register(RegisterRequest request) {
        ensureEmailAvailable(request.email(), null);
        ensureUsernameAvailable(request.username(), null);

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);

        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public Optional<UserDto> updateUser(Long id, UpdateUserRequest request, boolean allowRoleChanges) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    applyUpdate(existingUser, request, allowRoleChanges);
                    return userMapper.toDto(userRepository.save(existingUser));
                });
    }

    @Transactional
    public Optional<UserDto> updateCurrentUser(String email, UpdateUserRequest request) {
        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    applyUpdate(existingUser, request, false);
                    return userMapper.toDto(userRepository.save(existingUser));
                });
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    private void applyUpdate(User user, UpdateUserRequest request, boolean allowRoleChanges) {
        if (request.username() != null && !request.username().isBlank()) {
            ensureUsernameAvailable(request.username(), user.getId());
            user.setUsername(request.username());
        }

        if (request.email() != null && !request.email().isBlank()) {
            ensureEmailAvailable(request.email(), user.getId());
            user.setEmail(request.email());
        }

        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        if (allowRoleChanges && request.role() != null) {
            user.setRole(request.role());
        }

        if (allowRoleChanges && request.enabled() != null) {
            user.setEnabled(request.enabled());
        }
    }

    private void ensureEmailAvailable(String email, Long currentUserId) {
        userRepository.findByEmail(email)
                .filter(existing -> !existing.getId().equals(currentUserId))
                .ifPresent(existing -> {
                    throw new ConflictException("Email is already in use");
                });
    }

    private void ensureUsernameAvailable(String username, Long currentUserId) {
        userRepository.findByUsername(username)
                .filter(existing -> !existing.getId().equals(currentUserId))
                .ifPresent(existing -> {
                    throw new ConflictException("Username is already in use");
                });
    }
}
