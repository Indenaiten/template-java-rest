package com.codenaiten.template.rest.app.dto.result;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * DTO Result con la información de una página de resultados.
 *
 * @param <T> Extiende de {@link Serializable} y representa el tipo de contenido que va a contener la página de resultados.
 *
 * @see Serializable
 */
@Getter
public class PageResult<T extends Serializable> implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** EL número total de elementos existentes */
    private final Long total;

    /** El número total de páginas existentes */
    private final Long totalPages;

    /** El número actual de la página */
    private final Integer page;

    /** El tamaño máximo de la página */
    private final Integer size;

    /** El contenido de la página */
    private final List<T> content;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor de {@link PageResult}
     *
     * @param total {@link Long} que representa el número total de elementos existentes.
     * @param page {@link Integer} que representa el número de la página actual.
     * @param size {@link Integer} que representa el número máximo de elementos cargados en la página.
     * @param content {@link List} de elementos de tipo {@link T} que representa el contenido de la página.
     */
    public PageResult( final Long total, final Integer page, final Integer size, final List<T> content ){
        this.total = total;
        this.page = page;
        this.size = size;
        this.content = content;
        this.totalPages = ((total + size - 1) / size);
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}