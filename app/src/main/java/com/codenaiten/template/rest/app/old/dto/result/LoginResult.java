package com.codenaiten.template.rest.app.old.dto.result;

import com.codenaiten.template.rest.app.old.authentication.TokenInfo;

import java.util.UUID;

public record LoginResult(
        UUID accountId,
        UUID userId,
        TokenInfo tokenInfo
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
