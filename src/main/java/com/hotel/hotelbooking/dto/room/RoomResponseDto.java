package com.hotel.hotelbooking.dto.room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record RoomResponseDto(
        Long id,
        String name,
        String description,
        String number,
        BigDecimal price,
        int maxPeople,
        List<LocalDate> unavailableDates,
        Long hotelId,
        String hotelName
) {
}
