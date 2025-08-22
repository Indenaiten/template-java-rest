package com.codenaiten.template.rest.web.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema( description = "Información privada de un usuario" )
public record UserInfoResponse(
        @Schema( description = "Identificador único del usuario", example = "8e423b1a-ec10-4d92-a0cb-88d7e3e9f5a6" )
        UUID id,

        @Schema( description = "Rol del usuario representado como un número", example = "1" )
        Integer role,

        @Schema( description = "Nombre de usuario", example = "Example" )
        String username,

        @Schema( description = "Nombre real del usuario", example = "Real Name" )
        String name,

        @Schema( description = "Apellido del usuario", example = "Optional Surname", nullable = true )
        String surname,

        @Schema( description = "Fecha de nacimiento", example = "10/02/1993" )
        LocalDate birthdate,

        @Schema( description = "Fecha de creación (epoch millis)", type = "number", example = "1725403200000" )
        Long createdAt,

        @Schema( description = "Fecha de última modificación (epoch millis)", type = "number", example = "1725489600000" )
        Long updatedAt
){
}
