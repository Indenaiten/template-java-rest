package com.codenaiten.template.rest.web.rest.converter;

import com.codenaiten.template.rest.app.properties.TemporalFormatProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Convierte un {@link String} a un objeto de tipo {@link LocalTime}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalTimeConverter implements Converter<String, LocalTime>{

    /** Properties con los formatos de fecha y hora de la aplicación */
    private final TemporalFormatProperties temporalFormatProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public LocalTime convert( final String source ){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( this.temporalFormatProperties.getTime() );
        return Optional.ofNullable( source ).map( value -> LocalTime.parse( value, formatter )).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
