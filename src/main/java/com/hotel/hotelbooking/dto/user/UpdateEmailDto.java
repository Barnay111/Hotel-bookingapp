package com.hotel.hotelbooking.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateEmailDto(
        @NotBlank(message = "New email is required")
        @Email(message = "Email must be valid")
        @Size(max = 200, message = "Email is too long")
        String newEmail) {
}
