package com.hotel.hotelbooking.dto.room;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record RoomRequestDto(

        @NotBlank(message = "Name must not be blank")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Description must not be blank")
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @NotBlank(message = "Room number must not be blank")
        @Size(max = 20, message = "Room number must not exceed 20 characters")
        String number,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @Positive(message = "Max people must be positive")
        @Max(value = 20, message = "Max people must not exceed 20")
        int maxPeople,

        List<LocalDate> unavailableDates,

        @NotNull(message = "Hotel id is required")
        Long hotelId
) {
}
