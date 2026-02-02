package com.hotel.hotelbooking.dto.hotel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record HotelRatingDto(
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot be more than 5")
        int mark
) {
}
