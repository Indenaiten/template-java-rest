package com.codenaiten.template.rest.app.dto.result;

import com.codenaiten.template.rest.app.authentication.TokenInfo;

import java.util.UUID;

public record LoginResult(
        UUID accountId,
        UUID userId,
        TokenInfo tokenInfo
) {
}
