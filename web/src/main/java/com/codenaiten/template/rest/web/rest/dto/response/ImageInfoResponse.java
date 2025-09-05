package com.codenaiten.template.rest.web.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema( description = "Información de un usuario" )
public record ImageInfoResponse(

        @Schema( description = "Identificador único de la imagen de perfil del usuario", example = "24843dfd-3783-4d47-8577-7d14d6c509e5" )
        UUID id,

        @Schema( description = "Indentificador único del usuario que es propietario de la imagen", example = "8e423b1a-ec10-4d92-a0cb-88d7e3e9f5a6" )
        UUID owner,

        @Schema( description = "Content Type de la imagen", example = "image/jpeg" )
        String contentType,

        @Schema( description = "Content Size de la imagen", type = "number" )
        Long size,

        @Schema( description = "Fecha de creación (epoch millis)", type = "number", example = "1725403200000" )
        Long createdAt,

        @Schema( description = "Fecha de última modificación (epoch millis)", type = "number", example = "1725489600000" )
        Long updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
