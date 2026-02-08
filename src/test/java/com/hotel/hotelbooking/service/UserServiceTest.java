package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.user.*;
import com.hotel.hotelbooking.entity.User;
import com.hotel.hotelbooking.enums.RoleType;
import com.hotel.hotelbooking.mapper.UserMapperImpl;
import com.hotel.hotelbooking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserMapperImpl userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSaveUserWhenDataIsValid() {
        CreateUserDto dto = new CreateUserDto("Lawliet", "password11",
                "lawliet11@mail.com", RoleType.USER);

        when(userRepository.existsByUsername("Lawliet")).thenReturn(false);
        when(userRepository.existsByEmail("lawliet11@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("password11")).thenReturn("encoded_password11");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("Lawliet");
        savedUser.setEmail("lawliet11@mail.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("Lawliet", result.username());
        assertEquals("lawliet11@mail.com", result.email());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowExceptionWhenUsernameExists() {
        CreateUserDto dto = new CreateUserDto("Lawliet", "password11",
                "lawliet11@mail.com", RoleType.USER);
        when(userRepository.existsByUsername("Lawliet")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void updatePassword_shouldUpdateWhenOldPasswordMatches() {
        Long id = 1L;
        UpdatePasswordDto dto = new UpdatePasswordDto("password11", "newpassword");
        User user = new User();
        user.setPassword("encoded_old_password");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password11",
                "encoded_old_password")).thenReturn(true);
        when(passwordEncoder.encode("newpassword")).thenReturn("encoded_newpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updatePassword(id, dto);

        verify(passwordEncoder).encode("newpassword");
        verify(userRepository).save(user);
    }

    @Test
    void updateEmail_shouldThrowExceptionWhenEmailAlreadyExists() {
        Long id = 1L;
        UpdateEmailDto dto = new UpdateEmailDto("lawliet11@mail.com");

        when(userRepository.existsByEmail("lawliet11@mail.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateEmail(id, dto));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void updateUsername_shouldUpdateWhenUsernameIsUnique() {
        Long id = 1L;
        UpdateUsernameDto dto = new UpdateUsernameDto("Lawliet");
        User user = new User();
        user.setUsername("OldName");

        when(userRepository.existsByUsername("Lawliet")).thenReturn(false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.updateUsername(id, dto);
        assertEquals("Lawliet", user.getUsername());
        verify(userRepository).save(user);
    }
}