package com.codenaiten.template.rest.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Optional;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface OptionalMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| DEFAULT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el valor del {@link Optional} si existe, en caso contrario devuelve {@code null}.
     *
     * @param optional {@link Optional} que con el posible valor a recuperar.
     *
     * @return {@link T} que representa el valor del {@link Optional} si existe, en caso contrario {@code null}.
     *
     * @param <T> Tipo del valor contenido en el {@link Optional}.
     */
    default <T> T unwrap( final Optional<T> optional ){
        return optional.orElse( null );
    }

    /**
     * Crea un {@link Optional} con el valor proporcionado.
     *
     * @param value Valor a incluir en el {@link Optional}.
     *
     * @return {@link Optional} con el valor proporcionado.
     *
     * @param <T> Tipo del valor a incluir en el {@link Optional}.
     */
    default <T> Optional<T> wrap( final T value ){
        return Optional.ofNullable( value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
