package com.codenaiten.template.rest.app.dto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class UserInfoResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final UUID id;
    private final String username;
    private final String name;
    private final String surname;
    private final LocalDate birthdate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}