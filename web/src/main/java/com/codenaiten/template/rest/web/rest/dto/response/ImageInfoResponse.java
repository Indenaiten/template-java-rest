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
@Schema( description = "Información de un usuario" )
public class ImageInfoResponse implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Schema( description = "Identificador único de la imagen de perfil del usuario", example = "24843dfd-3783-4d47-8577-7d14d6c509e5" )
    private final UUID id;

    @Schema( description = "Indentificador único del usuario que es propietario de la imagen", example = "8e423b1a-ec10-4d92-a0cb-88d7e3e9f5a6" )
    private final UUID owner;

    @Schema( description = "Content Type de la imagen", example = "image/jpeg" )
    private final String contentType;

    @Schema( description = "Content Size de la imagen", type = "number" )
    private final Long size;

    @Schema( description = "Fecha de creación (epoch millis)", type = "number", example = "1725403200000" )
    private final Long createdAt;

    @Schema( description = "Fecha de última modificación (epoch millis)", type = "number", example = "1725489600000" )
    private final Long updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}
