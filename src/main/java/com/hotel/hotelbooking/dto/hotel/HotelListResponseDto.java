package com.hotel.hotelbooking.dto.hotel;

import java.util.List;

public record HotelListResponseDto(
        List<HotelResponseDto> hotels,
        long totalElements
) {
}

