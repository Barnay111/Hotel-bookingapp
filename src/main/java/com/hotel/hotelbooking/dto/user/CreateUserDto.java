package com.hotel.hotelbooking.dto.user;

import com.hotel.hotelbooking.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDto(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 200, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 200, message = "Email is too long")
        String email,

        @NotNull(message = "RoleType is required")
        RoleType roleType
) {
}
