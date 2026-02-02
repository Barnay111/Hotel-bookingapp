package com.hotel.hotelbooking.mapper;

import com.hotel.hotelbooking.dto.user.CreateUserDto;
import com.hotel.hotelbooking.dto.user.UpdateEmailDto;
import com.hotel.hotelbooking.dto.user.UpdatePasswordDto;
import com.hotel.hotelbooking.dto.user.UpdateRoleTypeDto;
import com.hotel.hotelbooking.dto.user.UpdateUsernameDto;
import com.hotel.hotelbooking.dto.user.UserDto;
import com.hotel.hotelbooking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserDto dto);

    UserDto toDto(User user);

    void updateEntityFromPasswordDto(UpdatePasswordDto dto, @MappingTarget User user);

    void updateEntityFromEmailDto(UpdateEmailDto dto, @MappingTarget User user);

    void updateEntityFromUsernameDto(UpdateUsernameDto dto, @MappingTarget User user);

    void updateEntityFromRoleTypeDto(UpdateRoleTypeDto dto, @MappingTarget User user);

}
