package com.hotel.hotelbooking.dto.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record HotelRequestDto(
        @NotBlank(message = "Hotel's name is blank")
        String name,

        @NotBlank(message = "Hotel's headline is blank")
        String headline,

        @NotBlank(message = "Hotel's city is blank")
        String city,

        @NotBlank(message = "Hotel's address is blank")
        String address,

        @NotNull(message = "Hotel's distance from center is null")
        @PositiveOrZero(message = "Distance can not be negative")
        Double distanceFromCenter
) {
}
