package com.hotel.hotelbooking.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(
        @NotBlank(message = "Old password is required")
        String oldPassword,

        @NotBlank(message = "New password is required")
        @Size(min = 6, max = 200, message = "New password must be at least 6 characters")
        String newPassword
) {
}
