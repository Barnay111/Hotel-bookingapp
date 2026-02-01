package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.dto.hotel.HotelListResponseDto;
import com.hotel.hotelbooking.dto.hotel.HotelRequestDto;
import com.hotel.hotelbooking.dto.hotel.HotelResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.exception.EntityNotFoundException;
import com.hotel.hotelbooking.mapper.HotelMapperImpl;
import com.hotel.hotelbooking.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Spy
    private HotelMapperImpl hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void getHotel_shouldReturnResponseDtoWhenExists() {
        Long id = 10L;
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName("Four Seasons");

        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));

        HotelResponseDto result = hotelService.getHotel(id);
        assertNotNull(hotel);
        assertEquals("Four Seasons", hotel.getName());
        verify(hotelRepository).findById(id);
    }


    @Test
    void getHotel_shouldThrowExceptionWhenNotFound() {
        Long id = 125L;
        when(hotelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> hotelService.getHotel(id));
    }

    @Test
    void updateHotel_shouldUpdateExistingHotel() {
        Long id = 225L;
        Hotel existingHotel = new Hotel();
        existingHotel.setName("The Connaught");
        existingHotel.setCity("London");
        existingHotel.setAddress("Carlos Place, Mayfair, London W1K 2AL");
        existingHotel.setRating(4.5);
        existingHotel.setDistanceFromCenter(50.0);
        when(hotelRepository.findById(id)).thenReturn(Optional.of(existingHotel));

        HotelRequestDto updateDto =
                new HotelRequestDto(
                        "Edgbaston House",
                        "Stylish boutique hotel in Birmingham",
                        "Birmingham",
                        "65 Highfield Rd, Edgbaston, Birmingham B15 3DP",
                        4.7,
                        100.23
                );
        HotelResponseDto updated = hotelService.updateHotel(id, updateDto);

        assertEquals(updateDto.name(), updated.name());
        assertEquals(updateDto.city(), updated.city());
        assertEquals(updateDto.address(), updated.address());
        assertEquals(updateDto.rating(), updated.rating());
        assertEquals(updateDto.distanceFromCenter(), updated.distanceFromCenter());

        verify(hotelRepository).save(any(Hotel.class));
    }

    @Test
    void deleteHotel_shouldInvokeRepositoryDelete() {
        Long id = 20L;
        Hotel hotel = new Hotel();
        hotel.setId(id);

        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));

        hotelService.deleteHotel(id);
        verify(hotelRepository).delete(hotel);
    }

    @Test
    void getAllHotels_shouldReturnMappedListResponse() {
        Pageable pageable = PageRequest.of(0, 5);
        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setName("The Ritz-Carlton");
        hotel1.setCity("Paris");
        hotel1.setAddress("15 Place Vendôme");
        hotel1.setDistanceFromCenter(234.54);

        Hotel hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setName("Marina Bay Sands");
        hotel2.setCity("Singapore");
        hotel2.setAddress("10 Bayfront Ave");
        hotel2.setDistanceFromCenter(127.98);

        Page<Hotel> page = new PageImpl<>(List.of(hotel1, hotel2), pageable, 2);
        when(hotelRepository.findAll(pageable)).thenReturn(page);

        HotelListResponseDto result = hotelService.getAllHotels(pageable);

        assertNotNull(result);
        assertEquals(2, result.hotels().size());
        assertEquals(2, result.totalElements());


        assertEquals("The Ritz-Carlton", result.hotels().get(0).name());
        assertEquals("Paris", result.hotels().get(0).city());

        assertEquals("Marina Bay Sands", result.hotels().get(1).name());
        assertEquals("Singapore", result.hotels().get(1).city());

        verify(hotelRepository).findAll(pageable);
    }
}
