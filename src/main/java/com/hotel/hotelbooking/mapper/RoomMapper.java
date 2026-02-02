package com.hotel.hotelbooking.mapper;

import com.hotel.hotelbooking.dto.room.RoomRequestDto;
import com.hotel.hotelbooking.dto.room.RoomResponseDto;
import com.hotel.hotelbooking.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Room toEntity(RoomRequestDto dto);


    @Mapping(target = "hotel", ignore = true)
    void updateRoom(RoomRequestDto dto, @MappingTarget Room room);

    @Mapping(target = "hotelId", source = "hotel.id")
    @Mapping(target = "hotelName", source = "hotel.name")
    RoomResponseDto toDto(Room room);
}
