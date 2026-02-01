package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.hotel.HotelListResponseDto;
import com.hotel.hotelbooking.dto.hotel.HotelRequestDto;
import com.hotel.hotelbooking.dto.hotel.HotelResponseDto;
import com.hotel.hotelbooking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/{id}")
    public HotelResponseDto getHotel(@PathVariable Long id) {
        return hotelService.getHotel(id);
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

    @GetMapping
    public HotelListResponseDto getAllHotels(Pageable pageable) {
        return hotelService.getAllHotels(pageable);
    }
}
