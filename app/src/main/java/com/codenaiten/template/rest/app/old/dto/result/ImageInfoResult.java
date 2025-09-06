package com.codenaiten.template.rest.app.old.dto.result;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImageInfoResult(
        UUID id,
        UUID owner,
        String contentType,
        Long size,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}