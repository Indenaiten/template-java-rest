package com.codenaiten.template.rest.app.dto.result;

import java.util.UUID;

public record RefreshTokenResult(
        UUID accountId,
        UUID userId,
        String accessToken,
        String refreshToken
) {
}