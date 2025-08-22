package com.codenaiten.template.rest.app.dto.result;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountInfoResult(
        UUID id,
        UserInfoResult owner,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
