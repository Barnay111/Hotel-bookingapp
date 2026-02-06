package com.hotel.hotelbooking.dto.event;

import java.time.LocalDate;

public record RoomBookingEventDto(
        Long userId,
        LocalDate checkIn,
        LocalDate checkOut
) {
}