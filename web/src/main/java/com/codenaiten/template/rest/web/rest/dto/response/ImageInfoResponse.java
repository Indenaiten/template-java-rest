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
@Schema( description = "Información de una imagen" )
public class ImageInfoResponse implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Schema( description = "Identificador único de la imagen", example = "8e423b1a-ec10-4d92-a0cb-88d7e3e9f5a6" )
    private final UUID id;

    @Schema( description = "El tipo de contenido de la imagen", example = "image/jpeg" )
    private final String contentType;

    @Schema( description = "Fecha de creación (epoch millis)", type = "number", example = "1725403200000" )
    private final Long createdAt;

    @Schema( description = "Fecha de última modificación (epoch millis)", type = "number", example = "1725489600000" )
    private final Long updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}
