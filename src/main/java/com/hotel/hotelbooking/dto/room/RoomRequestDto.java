package com.hotel.hotelbooking.dto.room;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record RoomRequestDto(

        @NotBlank(message = "Room is required")
        @Size(max = 100, message = "Room name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Description is required")
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @NotBlank(message = "Room number is required")
        @Size(max = 20, message = "Room number must not exceed 20 characters")
        String number,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        BigDecimal price,

        @Positive(message = "Maximum number of guests must be greater than 0")
        @Max(value = 20, message = "Maximum number of guests must not exceed 20")
        int maxPeople,

        List<LocalDate> unavailableDates,

        @NotNull(message = "Hotel ID is required")
        Long hotelId
) {
}
