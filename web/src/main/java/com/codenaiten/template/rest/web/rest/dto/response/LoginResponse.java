package com.codenaiten.template.rest.web.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
@Schema( description = "Información del login exitoso de un usuario" )
public class LoginResponse implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Schema( description = "Identificador único de la cuenta de usuario", example = "5d16313f-de31-40a3-9db6-28bc2e518ae1" )
    private final UUID accountId;

    @Schema( description = "Identificador único del usuario", example = "8e423b1a-ec10-4d92-a0cb-88d7e3e9f5a6" )
    private final UUID userId;

// ------------------------------------------------------------------------------------------------------------------ \\

}
