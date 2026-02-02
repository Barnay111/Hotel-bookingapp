package com.hotel.hotelbooking.dto.hotel;

public record HotelResponseDto(
        Long id,
        String name,
        String headline,
        String city,
        String address,
        Double distanceFromCenter,
        Double rating,
        Integer numberOfRating
) {
}
