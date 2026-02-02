package com.hotel.hotelbooking.dto.booking;

import java.time.LocalDate;

public record BookingResponseDto(
        Long id,
        Long roomId,
        Long userId,
        LocalDate checkIn,
        LocalDate checkOut
) {}
