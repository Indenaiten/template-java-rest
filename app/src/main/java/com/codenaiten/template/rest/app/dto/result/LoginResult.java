package com.codenaiten.template.rest.app.dto.result;

import com.codenaiten.template.rest.app.authentication.TokenInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class LoginResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final UUID accountId;
    private final UUID userId;
    private final TokenInfo tokenInfo;

// ------------------------------------------------------------------------------------------------------------------ \\

}
