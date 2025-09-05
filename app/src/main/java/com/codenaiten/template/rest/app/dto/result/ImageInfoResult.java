package com.codenaiten.template.rest.app.dto.result;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImageInfoResult(
        UUID id,
        String contentType,
        Long size,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}