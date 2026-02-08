package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.hotel.HotelListResponseDto;
import com.hotel.hotelbooking.dto.hotel.HotelRatingDto;
import com.hotel.hotelbooking.dto.hotel.HotelRequestDto;
import com.hotel.hotelbooking.dto.hotel.HotelResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.exception.InvalidRequestException;
import com.hotel.hotelbooking.filter.HotelFilterDto;
import com.hotel.hotelbooking.mapper.HotelMapper;
import com.hotel.hotelbooking.repository.HotelRepository;
import com.hotel.hotelbooking.specification.HotelSpecification;
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
public class HotelService {
    private static final double ROUNDING_FACTOR = 10.0;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Hotel getHotel(Long hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(
                () -> new EntityNotFoundException("Hotel with id " + hotelId + " does not exist"));
    }

    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = getHotel(id);
        hotelRepository.delete(hotel);
    }

    @Transactional(readOnly = true)
    public HotelListResponseDto getAllHotels(Pageable pageable) {
        Page<Hotel> page = hotelRepository.findAll(pageable);
        List<HotelResponseDto> hotels = page.getContent()
                .stream()
                .map(hotelMapper::toResponseDto)
                .toList();

        return new HotelListResponseDto(hotels, page.getTotalElements());
    }

    @Transactional
    public HotelResponseDto updateHotelRating(Long hotelId, HotelRatingDto ratingDto) {
        Hotel hotel = getHotel(hotelId);
        int newMark = ratingDto.mark();

        if (newMark < MIN_RATING || newMark > MAX_RATING) {
            throw new InvalidRequestException("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
        }

        double totalRating = hotel.getRating() * hotel.getNumberOfRating();
        totalRating += newMark;
        int newNumberOfRating = hotel.getNumberOfRating() + 1;
        hotel.setNumberOfRating(newNumberOfRating);
        double newRating = Math.round((totalRating / newNumberOfRating) * ROUNDING_FACTOR) / ROUNDING_FACTOR;
        hotel.setRating(newRating);

        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toResponseDto(savedHotel);
    }


    @Transactional(readOnly = true)
    public HotelListResponseDto getAllHotelsFiltered(HotelFilterDto filter, Pageable pageable) {
        Page<Hotel> page = hotelRepository.findAll(HotelSpecification.filterBy(filter), pageable);

        List<HotelResponseDto> hotels = page.getContent().stream()
                .map(hotelMapper::toResponseDto)
                .toList();

        return new HotelListResponseDto(hotels, page.getTotalElements());
    }
}
