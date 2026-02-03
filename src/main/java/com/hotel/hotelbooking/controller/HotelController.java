package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.hotel.HotelListResponseDto;
import com.hotel.hotelbooking.dto.hotel.HotelRatingDto;
import com.hotel.hotelbooking.dto.hotel.HotelRequestDto;
import com.hotel.hotelbooking.dto.hotel.HotelResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.filter.HotelFilterDto;
import com.hotel.hotelbooking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/{id}")
    public HotelResponseDto getHotel(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponseDto createHotel(@Valid @RequestBody HotelRequestDto requestDto) {
        return hotelService.createHotel(requestDto);
    }

    @PutMapping("/{id}")
    public HotelResponseDto updateHotel(@PathVariable Long id,
                                        @Valid @RequestBody HotelRequestDto requestDto) {
        return hotelService.updateHotel(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }

    @PutMapping("/{id}/rating")
    @PreAuthorize("isAuthenticated()")
    public HotelResponseDto updateRating(@PathVariable Long id, @Valid @RequestBody HotelRatingDto dto) {
        return hotelService.updateHotelRating(id, dto);
    }

    @GetMapping
    public HotelListResponseDto getAllHotels(Pageable pageable) {
        return hotelService.getAllHotels(pageable);
    }

    @GetMapping("/search")
    public HotelListResponseDto searchHotels(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String headline,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double distanceFromCenterMin,
            @RequestParam(required = false) Double distanceFromCenterMax,
            @RequestParam(required = false) Double ratingMin,
            @RequestParam(required = false) Double ratingMax,
            @RequestParam(required = false) Integer numberOfRatingMin,
            @RequestParam(required = false) Integer numberOfRatingMax,
            Pageable pageable
    ) {
        HotelFilterDto filter = HotelFilterDto.builder()
                .id(id)
                .name(name)
                .headline(headline)
                .city(city)
                .address(address)
                .distanceFromCenterMin(distanceFromCenterMin)
                .distanceFromCenterMax(distanceFromCenterMax)
                .ratingMin(ratingMin)
                .ratingMax(ratingMax)
                .numberOfRatingMin(numberOfRatingMin)
                .numberOfRatingMax(numberOfRatingMax)
                .build();

        return hotelService.getAllHotelsFiltered(filter, pageable);
    }

}
