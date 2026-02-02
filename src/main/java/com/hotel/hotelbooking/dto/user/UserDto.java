package com.hotel.hotelbooking.dto.user;

import com.hotel.hotelbooking.enums.RoleType;

import java.time.LocalDateTime;

public record UserDto(Long id,
                      String username,
                      String email,
                      RoleType roleType,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt
) {
}
