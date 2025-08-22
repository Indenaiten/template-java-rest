package com.codenaiten.template.rest.app.dto.result;

import java.util.UUID;

public record LoginResult(
        UUID accountId,
        UUID userId,
        String token
) {
}
