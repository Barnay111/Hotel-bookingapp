package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.user.CreateUserDto;
import com.hotel.hotelbooking.dto.user.UpdateEmailDto;
import com.hotel.hotelbooking.dto.user.UpdatePasswordDto;
import com.hotel.hotelbooking.dto.user.UpdateRoleTypeDto;
import com.hotel.hotelbooking.dto.user.UpdateUsernameDto;
import com.hotel.hotelbooking.dto.user.UserDto;
import com.hotel.hotelbooking.entity.User;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.mapper.UserMapper;
import com.hotel.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUserById(Long id) {
        User user = getUser(id);
        return userMapper.toDto(user);
    }

    public UserDto createUser(CreateUserDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserDto updatePassword(Long id, UpdatePasswordDto dto) {
        User user = getUser(id);

        if (!user.getPassword().equals(dto.oldPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(dto.newPassword());
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public UserDto updateEmail(Long id, UpdateEmailDto dto) {
        if (userRepository.existsByEmail(dto.newEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = getUser(id);
        user.setEmail(dto.newEmail());
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public UserDto updateUsername(Long id, UpdateUsernameDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = getUser(id);
        user.setUsername(dto.username());
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public UserDto updateRole(Long id, UpdateRoleTypeDto dto) {
        User user = getUser(id);
        user.setRoleType(dto.roleType());
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id" + userId + " does not exist"));
    }
}
