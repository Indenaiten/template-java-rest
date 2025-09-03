package com.codenaiten.template.rest.app.dto.command;

import java.util.Optional;

/**
 * DTO Command que contiene los datos de paginación para las consultas.
 *
 * @param page {@link Integer} que representa el número de la página de la consulta.
 * @param size {@link Integer} que representa el tamaño máximo de la página de la consulta.
 */
public record PageableCommand(
        Integer page,
        Integer size
) {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Crea una instancia de {@link PageableCommand} con los valores proporcionados, o con los valores por defecto si
     * son nulos.
     *
     * @param page {@link Integer} que representa el número de la página de la consulta, si es {@code null} se establece
     *             el valor {@code 0} por defecto.
     * @param size {@link Integer} que representa tamaño máximo de la página de la consulta, si es {@code null} se
     *             establece el valor {@code 25} por defecto.
     *
     * @return {@link PageableCommand} con los valores proporcionados, con los valores por defecto en caso de recibir
     *         valores {@code null}.
     */
    public static PageableCommand of( Integer page, Integer size ){
        page = Optional.ofNullable( page ).orElse( 0 );
        size = Optional.ofNullable( size ).orElse( 25 );
        return new PageableCommand( page, size );
    }

    /**
     * Crea una instancia de {@link PageableCommand} con el valor de página proporcionado, o con el valor por defecto si
     * es {@code null}. El tamaño de la página se establece con el valor por defecto ({@code 25}).
     *
     * @param page {@link Integer} que representa el número de la página de la consulta, si es {@code null} se establece
     *             el valor {@code 0} por defecto.
     *
     * @return {@link PageableCommand} con el valor de página proporcionado, o con el valor por defecto en caso de
     *         recibir un valor {@code null}. El tamaño de la página se establece con el valor por defecto ({@code 25}).
     */
    public static PageableCommand of(Integer page ){
        page = Optional.ofNullable( page ).orElse( 0 );
        return of( page, null );
    }

    /**
     * Crea una instancia de {@link PageableCommand} con la página con el valor por defecto ({@code 0}, y el tamaño de
     * la página con el valor por defecto {@code 25}).
     *
     * @return {@link PageableCommand} con el valor de página por defecto ({@code 0}) y el tamaño de la página con el
     *         valor por defecto ({@code 25}).
     */
    public static PageableCommand create(){
        return of( null, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
