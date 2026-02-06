package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.booking.BookingRequestDto;
import com.hotel.hotelbooking.dto.booking.BookingResponseDto;
import com.hotel.hotelbooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
@PreAuthorize("isAuthenticated()")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{id}")
    public BookingResponseDto getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    public BookingResponseDto createBooking(
            @Valid @RequestBody BookingRequestDto dto
    ) {
        return bookingService.createBooking(dto);
    }

    @GetMapping
    public List<BookingResponseDto> getAllBookings() {
        return bookingService.getAllBookings();
    }
}
