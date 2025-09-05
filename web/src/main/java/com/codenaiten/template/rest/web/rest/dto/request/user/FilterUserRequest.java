package com.codenaiten.template.rest.web.rest.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Petición para filtrar una búsqueda de usuarios" )
public record FilterUserRequest(

        @Schema( description = "Nickname de usuario único", example = "user" )
        String username,

        @Schema( description = "Nombre del usuario", example = "name" )
        String name,

        @Schema( description = "Apellido del usuario", example = "surname" )
        String surname
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
