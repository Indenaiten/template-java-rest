package com.codenaiten.template.rest.app.dto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class AccountInfoResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final UUID id;
    private final UserInfoResult owner;
    private final String lang;
    private final Integer role;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}
