package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.user.CreateUserDto;
import com.hotel.hotelbooking.dto.user.UpdateEmailDto;
import com.hotel.hotelbooking.dto.user.UpdatePasswordDto;
import com.hotel.hotelbooking.dto.user.UpdateRoleTypeDto;
import com.hotel.hotelbooking.dto.user.UpdateUsernameDto;
import com.hotel.hotelbooking.dto.user.UserDto;
import com.hotel.hotelbooking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody CreateUserDto dto) {
        return userService.createUser(dto);
    }

    @PutMapping("/{id}/password")
    public UserDto updatePassword(@PathVariable Long id,
                                  @Valid @RequestBody UpdatePasswordDto dto) {
        return userService.updatePassword(id, dto);
    }

    @PutMapping("/{id}/email")
    public UserDto updateEmail(@PathVariable Long id,
                               @Valid @RequestBody UpdateEmailDto dto) {
        return userService.updateEmail(id, dto);
    }


    @PutMapping("/{id}/username")
    public UserDto updateUsername(@PathVariable Long id,
                                  @Valid @RequestBody UpdateUsernameDto dto) {
        return userService.updateUsername(id, dto);
    }

    @PutMapping("/{id}/role")
    public UserDto updateRole(@PathVariable Long id,
                              @Valid @RequestBody UpdateRoleTypeDto dto) {
        return userService.updateRole(id, dto);
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
