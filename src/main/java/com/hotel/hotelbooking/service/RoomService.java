package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.room.RoomRequestDto;
import com.hotel.hotelbooking.dto.room.RoomResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.entity.Room;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.filter.RoomFilterDto;
import com.hotel.hotelbooking.mapper.RoomMapper;
import com.hotel.hotelbooking.repository.RoomRepository;
import com.hotel.hotelbooking.specification.RoomSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;

    @Transactional(readOnly = true)
    public RoomResponseDto getRoomById(Long id) {
        Room room = getRoom(id);
        return roomMapper.toDto(room);
    }

    @Transactional
    public RoomResponseDto createRoom(RoomRequestDto requestDto) {
        Room room = roomMapper.toEntity(requestDto);
        Hotel hotel = hotelService.getHotel(requestDto.hotelId());
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);

        return roomMapper.toDto(savedRoom);
    }

    @Transactional
    public RoomResponseDto updateRoom(RoomRequestDto requestDto, Long id) {
        Room room = getRoom(id);
        roomMapper.updateRoom(requestDto, room);

        Hotel hotel = hotelService.getHotel(requestDto.hotelId());
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);

        return roomMapper.toDto(savedRoom);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        Room room = getRoom(roomId);
        roomRepository.delete(room);
    }

    @Transactional(readOnly = true)
    public Page<RoomResponseDto> getRoomsFiltered(RoomFilterDto filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Room> roomsPage = roomRepository.findAll(
                RoomSpecification.filterBy(filter), pageable
        );

        List<RoomResponseDto> roomDtos = roomsPage.getContent().stream()
                .map(roomMapper::toDto)
                .toList();

        return new PageImpl<>(roomDtos, pageable, roomsPage.getTotalElements());
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id: " + roomId + " does not exist"));
    }
}
