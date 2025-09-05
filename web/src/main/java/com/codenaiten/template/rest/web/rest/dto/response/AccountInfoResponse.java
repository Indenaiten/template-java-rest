package com.codenaiten.template.rest.web.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema( description = "Información de una cuenta de usuario" )
public record AccountInfoResponse(

        @Schema( description = "Identificador único de la cuenta de usuario", example = "d33787a2-fc5d-4f35-8e16-0192f7cfd530" )
        UUID id,

        @Schema( description = "Propietario de la cuenta", implementation = UserInfoResponse.class )
        UserInfoResponse owner,

        @Schema( description = "Lenguaje de la cuenta de usuario", example = "es" )
        String lang,

        @Schema( description = "Rol de la cuenta de usuario representado como un número", example = "1" )
        Integer role,

        @Schema( description = "Email de la cuenta de usuario", example = "example@domain.cc" )
        String email,

        @Schema( description = "Fecha de creación (epoch millis)", type = "number", example = "1725403200000" )
        Long createdAt,

        @Schema( description = "Fecha de última modificación (epoch millis)", type = "number", example = "1725489600000" )
        Long updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
