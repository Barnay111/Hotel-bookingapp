package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.room.RoomRequestDto;
import com.hotel.hotelbooking.dto.room.RoomResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.entity.Room;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.mapper.RoomMapperImpl;
import com.hotel.hotelbooking.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelService hotelService;

    @Spy
    private RoomMapperImpl roomMapper;

    @InjectMocks
    private RoomService roomService;


    @Test
    void getById_shouldReturnRoomResponseDtoWhenExists() {
        Long roomId = 1L;
        Room room = new Room();
        room.setId(roomId);
        room.setName("Presidential Suite");

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("The Ritz London");
        room.setHotel(hotel);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        RoomResponseDto result = roomService.getRoomById(roomId);

        assertNotNull(result);
        assertEquals("Presidential Suite", result.name());
        assertEquals("The Ritz London", result.hotelName());
        verify(roomRepository).findById(roomId);
    }

    @Test
    void getById_shouldThrowExceptionWhenRoomNotFound() {
        Long roomId = 30L;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roomService.getRoomById(roomId));
    }

    @Test
    void createRoom_shouldSaveAndReturnRoomResponseDto() {
        RoomRequestDto requestDto = new RoomRequestDto(
                "Royal King Room",
                "A luxurious room with city view",
                "501",
                new BigDecimal("350.00"),
                2,
                List.of(LocalDate.now(), LocalDate.now().plusDays(1)),
                2L
        );

        Hotel hotel = new Hotel();
        hotel.setId(2L);
        hotel.setName("Marriott Marquis New York");

        when(hotelService.getHotel(2L)).thenReturn(hotel);
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RoomResponseDto result = roomService.createRoom(requestDto);

        assertNotNull(result);
        assertEquals("Royal King Room", result.name());
        assertEquals(2L, result.hotelId());
        assertEquals("Marriott Marquis New York", result.hotelName());
        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void updateRoom_shouldUpdateExistingRoom() {
        Long roomId = 5L;
        Room existingRoom = new Room();
        existingRoom.setId(roomId);
        existingRoom.setName("Standard Room");
        Hotel oldHotel = new Hotel();
        oldHotel.setId(1L);
        oldHotel.setName("Hilton London");
        existingRoom.setHotel(oldHotel);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));

        RoomRequestDto updateDto = new RoomRequestDto(
                "Deluxe Ocean View",
                "Spacious room with balcony overlooking the ocean",
                "1203",
                new BigDecimal("450.00"),
                3,
                List.of(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3)),
                3L
        );

        Hotel newHotel = new Hotel();
        newHotel.setId(3L);
        newHotel.setName("Atlantis Paradise Island");
        when(hotelService.getHotel(3L)).thenReturn(newHotel);
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RoomResponseDto updated = roomService.updateRoom(updateDto, roomId);

        assertEquals("Deluxe Ocean View", updated.name());
        assertEquals(3, updated.maxPeople());
        assertEquals(3L, updated.hotelId());
        assertEquals("Atlantis Paradise Island", updated.hotelName());
        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void deleteRoom_shouldInvokeRepositoryDelete() {
        Long roomId = 10L;
        Room room = new Room();
        room.setId(roomId);

        Hotel hotel = new Hotel();
        hotel.setId(4L);
        hotel.setName("Four Seasons Tokyo");
        room.setHotel(hotel);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        roomService.deleteRoom(roomId);
        verify(roomRepository).delete(room);
    }
}
