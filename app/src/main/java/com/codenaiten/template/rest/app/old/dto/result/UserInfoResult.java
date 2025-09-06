package com.codenaiten.template.rest.app.old.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserInfoResult(
        UUID id,
        UUID image,
        String username,
        String name,
        String surname,
        LocalDate birthdate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}