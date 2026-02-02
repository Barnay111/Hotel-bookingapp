package com.hotel.hotelbooking.service;


import com.hotel.hotelbooking.dto.booking.BookingRequestDto;
import com.hotel.hotelbooking.dto.booking.BookingResponseDto;
import com.hotel.hotelbooking.entity.Booking;
import com.hotel.hotelbooking.entity.Room;
import com.hotel.hotelbooking.entity.User;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.exception.IllegalStateException;
import com.hotel.hotelbooking.mapper.BookingMapper;
import com.hotel.hotelbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomService roomService;
    private final UserService userService;

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto dto) {

        if (!dto.checkOut().isAfter(dto.checkIn())) {
            throw new IllegalStateException("Check-out date must be after check-in date");
        }

        Room room = roomService.getRoom(dto.roomId());
        User user = userService.getUser(dto.userId());

        boolean conflict = bookingRepository.existsConflict(
                dto.roomId(), dto.checkIn(), dto.checkOut()
        );

        if (conflict) {
            throw new IllegalStateException("Room is already booked for the selected dates");
        }

        Booking booking = bookingMapper.toEntity(dto);
        booking.setRoom(room);
        booking.setUser(user);
        booking = bookingRepository.save(booking);

        return bookingMapper.toDto(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponseDto> getAllBookings() {
        return bookingMapper.toDtoList(bookingRepository.findAll());
    }

    public BookingResponseDto getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking with id: " + bookingId + " does not exist"));
        return bookingMapper.toDto(booking);
    }
}
