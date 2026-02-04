package com.hotel.hotelbooking.filter;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record RoomFilterDto(
        Long id,
        String name,
        BigDecimal priceMin,
        BigDecimal priceMax,
        Integer maxPeople,
        LocalDate checkIn,
        LocalDate checkOut,
        Long hotelId
) {
}
