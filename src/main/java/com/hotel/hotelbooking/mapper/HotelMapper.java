package com.hotel.hotelbooking.mapper;

import com.hotel.hotelbooking.dto.hotel.HotelRequestDto;
import com.hotel.hotelbooking.dto.hotel.HotelResponseDto;
import com.hotel.hotelbooking.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    Hotel toEntity(HotelRequestDto dto);

    HotelResponseDto toResponseDto(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewsCount", ignore = true)
    void updateHotel(HotelRequestDto dto, @MappingTarget Hotel hotel);
}
