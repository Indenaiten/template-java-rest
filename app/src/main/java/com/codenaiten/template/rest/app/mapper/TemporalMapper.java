package com.codenaiten.template.rest.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class })
public interface TemporalMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| DEFAULT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Convierte un {@link LocalDateTime} a un {@link Long} que representa el tiempo en milisegundos.
     *
     * @param dateTime {@link LocalDateTime} que representa el tiempo.
     *
     * @return {@link Long} que representa el tiempo en milisegundos.
     */
    default Long toEpochMilli( final LocalDateTime dateTime ){
        return Optional.ofNullable( dateTime )
                .map( value -> dateTime.atZone( ZoneId.systemDefault() ).toInstant().toEpochMilli() )
                .orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
