package com.hotel.hotelbooking.mapper;

import com.hotel.hotelbooking.dto.user.CreateUserDto;
import com.hotel.hotelbooking.dto.user.UpdateEmailDto;
import com.hotel.hotelbooking.dto.user.UpdatePasswordDto;
import com.hotel.hotelbooking.dto.user.UpdateRoleTypeDto;
import com.hotel.hotelbooking.dto.user.UpdateUsernameDto;
import com.hotel.hotelbooking.dto.user.UserDto;
import com.hotel.hotelbooking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(CreateUserDto dto);

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "roleType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromPasswordDto(UpdatePasswordDto dto, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roleType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromEmailDto(UpdateEmailDto dto, @MappingTarget User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "roleType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromUsernameDto(UpdateUsernameDto dto, @MappingTarget User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRoleTypeDto(UpdateRoleTypeDto dto, @MappingTarget User user);
}
