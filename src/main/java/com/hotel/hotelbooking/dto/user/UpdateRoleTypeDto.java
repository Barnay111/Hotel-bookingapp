package com.hotel.hotelbooking.dto.user;

import com.hotel.hotelbooking.enums.RoleType;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleTypeDto(
        @NotNull(message = "RoleType is required")
        RoleType roleType
) {
}
