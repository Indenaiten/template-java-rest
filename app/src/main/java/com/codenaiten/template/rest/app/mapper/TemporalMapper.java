package com.codenaiten.template.rest.app.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper( componentModel = "spring" )
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
        return dateTime.atZone( ZoneId.systemDefault() ).toInstant().toEpochMilli();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
