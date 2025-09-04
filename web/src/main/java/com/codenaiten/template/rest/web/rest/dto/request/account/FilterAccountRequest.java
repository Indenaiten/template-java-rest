package com.codenaiten.template.rest.web.rest.dto.request.account;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Petición para filtrar la consulta de las cuentas de usuario" )
public record FilterAccountRequest(

        @Schema( description = "Nickname de usuario único", example = "root" )
        String username,

        @Schema( description = "Rol de la cuenta de usuario", type="number", example = "2" )
        Integer role,

        @Schema( description = "Lenguaje de la cuenta de usuario", example = "es" )
        String lang,

        @Schema( description = "Email de usuario", example = "root@maildrop.cc" )
        String email,

        @Schema( description = "Nombre del usuario", example = "Root" )
        String name,

        @Schema( description = "Apellido del usuario", example = "Admin" )
        String surname
) {}

