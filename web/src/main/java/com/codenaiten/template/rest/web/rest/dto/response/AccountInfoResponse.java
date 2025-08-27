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
@Schema( description = "Información de una cuenta de usuario" )
public class AccountInfoResponse implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Schema( description = "Identificador único de la cuenta de usuario", example = "d33787a2-fc5d-4f35-8e16-0192f7cfd530" )
    private final UUID id;

    @Schema( description = "Propietario de la cuenta", implementation = UserInfoResponse.class )
    private final UserInfoResponse owner;

    @Schema( description = "Lenguaje de la cuenta de usuario", example = "es" )
    private final String lang;

    @Schema( description = "Rol de la cuenta de usuario representado como un número", example = "1" )
    private final Integer role;

    @Schema( description = "Email de la cuenta de usuario", example = "example@domain.cc" )
    private final String email;

    @Schema( description = "Fecha de creación (epoch millis)", type = "number", example = "1725403200000" )
    private final Long createdAt;

    @Schema( description = "Fecha de última modificación (epoch millis)", type = "number", example = "1725489600000" )
    private final Long updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}
