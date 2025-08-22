package com.codenaiten.template.rest.app.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserInfoResult(
        UUID id,
        Integer role,
        String username,
        String name,
        String surname,
        LocalDate birthdate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
