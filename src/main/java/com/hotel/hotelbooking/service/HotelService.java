package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.hotel.HotelListResponseDto;
import com.hotel.hotelbooking.dto.hotel.HotelRequestDto;
import com.hotel.hotelbooking.dto.hotel.HotelResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.mapper.HotelMapper;
import com.hotel.hotelbooking.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelResponseDto getHotelById(Long id) {
        Hotel hotel = getHotel(id);

        return hotelMapper.toResponseDto(hotel);
    }

    @Transactional
    public HotelResponseDto createHotel(HotelRequestDto requestDto) {
        Hotel hotel = hotelMapper.toEntity(requestDto);
        hotelRepository.save(hotel);

        return hotelMapper.toResponseDto(hotel);
    }

    @Transactional
    public HotelResponseDto updateHotel(Long id, HotelRequestDto requestDto) {
        Hotel hotel = getHotel(id);
        hotelMapper.updateHotel(requestDto, hotel);
        hotelRepository.save(hotel);

        return hotelMapper.toResponseDto(hotel);
    }

    public Hotel getHotel(Long hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(
                () -> new EntityNotFoundException("Hotel with id " + hotelId + " does not exist"));
    }

    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = getHotel(id);
        hotelRepository.delete(hotel);
    }

    public HotelListResponseDto getAllHotels(Pageable pageable) {
        Page<Hotel> page = hotelRepository.findAll(pageable);
        List<HotelResponseDto> hotels = page.getContent()
                .stream()
                .map(hotelMapper::toResponseDto)
                .toList();

        return new HotelListResponseDto(hotels, page.getTotalElements());
    }
}
