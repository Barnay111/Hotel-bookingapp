package com.hotel.hotelbooking.dto.booking;

import java.time.LocalDate;

public record BookingFullResponseDto(
        Long id,
        String roomName,
        String username,
        LocalDate checkIn,
        LocalDate checkOut
) {
}
