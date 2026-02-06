package com.hotel.hotelbooking.dto.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record HotelRequestDto(
        @NotBlank(message = "Hotel name is required")
        String name,

        @NotBlank(message = "Headline is required")
        String headline,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Address is required")
        String address,

        @NotNull(message = "Distance from center is required")
        @PositiveOrZero(message = "Distance from center must be zero or positive")
        Double distanceFromCenter
) {
}
