package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.room.RoomRequestDto;
import com.hotel.hotelbooking.dto.room.RoomResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.entity.Room;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.mapper.RoomMapper;
import com.hotel.hotelbooking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;

    @Transactional(readOnly = true)
    public RoomResponseDto getRoomById(Long id) {
        Room room = getRoom(id);
        return roomMapper.toDto(room);
    }

    public RoomResponseDto createRoom(RoomRequestDto requestDto) {
        Room room = roomMapper.toEntity(requestDto);
        Hotel hotel = hotelService.getHotel(requestDto.hotelId());
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);

        return roomMapper.toDto(savedRoom);
    }

    public RoomResponseDto updateRoom(RoomRequestDto requestDto, Long id) {
        Room room = getRoom(id);
        roomMapper.updateRoom(requestDto, room);

        Hotel hotel = hotelService.getHotel(requestDto.hotelId());
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);

        return roomMapper.toDto(savedRoom);
    }

    public void deleteRoom(Long roomId) {
        Room room = getRoom(roomId);
        roomRepository.delete(room);
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id: " + roomId + " does not exist"));
    }
}
