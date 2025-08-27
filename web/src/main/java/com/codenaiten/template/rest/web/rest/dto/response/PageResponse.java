package com.codenaiten.template.rest.web.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@Schema( description = "Respuesta paginada con metadatos de paginación" )
public class PageResponse<T extends Serializable> implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Schema( description = "Número de página actual (comienza en 0)", example = "0" )
    private final Integer page;

    @Schema( description = "Tamaño de la página", example = "20" )
    private final Integer size;

    @Schema( description = "Número total de elementos", example = "100" )
    private final Long total;

    @Schema( description = "Número total de páginas", example = "5" )
    private final Long totalPages;

    @Schema( description = "Lista de elementos de la página actual" )
    private final List<T> content;

// ------------------------------------------------------------------------------------------------------------------ \\

}
