package com.hotel.hotelbooking.dto.booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingRequestDto(


        @NotNull(message = "Room id is required")
        Long roomId,

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Check-in date is required")
        @FutureOrPresent(message = "Check-in date cannot be in the past")
        LocalDate checkIn,

        @NotNull(message = "Check-out date is required")
        @Future(message = "Check-out date must be in the future")
        LocalDate checkOut
) {}
