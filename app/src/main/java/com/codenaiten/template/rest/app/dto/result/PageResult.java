package com.codenaiten.template.rest.app.dto.result;

import java.util.List;

public record PageResult<T>(
    Long total,
    Long totalPages,
    Integer page,
    Integer size,
    List<T> content
){

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static <T> PageResult<T> of( final Long total, final Integer page, final Integer size, final List<T> content ){
        final Long totalPages = (total + size - 1) / size;
        return new PageResult<>( total, totalPages, page, size, content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}