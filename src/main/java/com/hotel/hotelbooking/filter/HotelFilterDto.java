package com.hotel.hotelbooking.filter;

import lombok.Builder;

@Builder
public record HotelFilterDto(
        Long id,
        String name,
        String headline,
        String city,
        String address,
        Double distanceFromCenterMin,
        Double distanceFromCenterMax,
        Double ratingMin,
        Double ratingMax,
        Integer numberOfRatingMin,
        Integer numberOfRatingMax
) {
}
