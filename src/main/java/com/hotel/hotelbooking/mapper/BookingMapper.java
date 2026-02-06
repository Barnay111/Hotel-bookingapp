package com.hotel.hotelbooking.mapper;

import com.hotel.hotelbooking.dto.booking.BookingFullResponseDto;
import com.hotel.hotelbooking.dto.booking.BookingRequestDto;
import com.hotel.hotelbooking.dto.booking.BookingResponseDto;
import com.hotel.hotelbooking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    Booking toEntity(BookingRequestDto dto);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    BookingResponseDto toDto(Booking booking);

    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "user.username", target = "username")
    BookingFullResponseDto toFullDto(Booking booking);

    List<BookingResponseDto> toDtoList(List<Booking> bookings);
    List<BookingFullResponseDto> toFullDtoList(List<Booking> bookings);
}
