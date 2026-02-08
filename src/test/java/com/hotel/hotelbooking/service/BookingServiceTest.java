package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.booking.BookingRequestDto;
import com.hotel.hotelbooking.dto.booking.BookingResponseDto;
import com.hotel.hotelbooking.entity.Booking;
import com.hotel.hotelbooking.entity.Room;
import com.hotel.hotelbooking.entity.User;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.exception.BookingConflictException;
import com.hotel.hotelbooking.mapper.BookingMapperImpl;
import com.hotel.hotelbooking.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Spy
    private BookingMapperImpl bookingMapper;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void createBooking_Success() {
        Room room = new Room();
        room.setId(11L);

        User user = new User();
        user.setId(20L);

        Booking booking = new Booking();
        booking.setId(45L);
        booking.setRoom(room);
        booking.setUser(user);

        BookingRequestDto dto = new BookingRequestDto(
                11L,
                20L,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
        );

        when(roomService.getRoom(dto.roomId())).thenReturn(room);
        when(userService.getUser(dto.userId())).thenReturn(user);
        when(bookingRepository.existsConflict(dto.roomId(), dto.checkIn(), dto.checkOut())).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingResponseDto response = bookingService.createBooking(dto);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(45L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void createBooking_CheckOutBeforeCheckIn_ThrowsException() {
        BookingRequestDto dto = new BookingRequestDto(
                11L,
                20L,
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(1)
        );

        BookingConflictException exception = assertThrows(
                BookingConflictException.class,
                () -> bookingService.createBooking(dto)
        );

        assertThat(exception.getMessage()).isEqualTo("Check-out date must be after check-in date");
    }

    @Test
    void createBooking_RoomConflict_ThrowsException() {
        Room room = new Room();
        room.setId(11L);

        User user = new User();
        user.setId(20L);

        BookingRequestDto dto = new BookingRequestDto(
                11L,
                20L,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
        );

        when(roomService.getRoom(dto.roomId())).thenReturn(room);
        when(userService.getUser(dto.userId())).thenReturn(user);
        when(bookingRepository.existsConflict(dto.roomId(), dto.checkIn(), dto.checkOut())).thenReturn(true);

        BookingConflictException exception = assertThrows(
                BookingConflictException.class,
                () -> bookingService.createBooking(dto)
        );

        assertThat(exception.getMessage()).isEqualTo("Room is already booked for the selected dates");
    }

    @Test
    void getAllBookings_ReturnsList() {
        Room room = new Room();
        room.setId(11L);

        User user = new User();
        user.setId(20L);

        Booking booking = new Booking();
        booking.setId(100L);
        booking.setRoom(room);
        booking.setUser(user);

        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<BookingResponseDto> result = bookingService.getAllBookings();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(100L);
    }

    @Test
    void getBookingById_Found() {
        Room room = new Room();
        room.setId(11L);

        User user = new User();
        user.setId(20L);

        Booking booking = new Booking();
        booking.setId(45L);
        booking.setRoom(room);
        booking.setUser(user);

        when(bookingRepository.findById(45L)).thenReturn(Optional.of(booking));

        BookingResponseDto response = bookingService.getBookingById(45L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(45L);
    }

    @Test
    void getBookingById_NotFound_ThrowsException() {
        when(bookingRepository.findById(100L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookingService.getBookingById(100L)
        );

        assertThat(exception.getMessage()).isEqualTo("Booking with id: 100 does not exist");
    }
}
