package com.codenaiten.template.rest.web.rest.converter;

import com.codenaiten.template.rest.app.old.properties.TemporalFormatProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Convierte un {@link String} a un objeto de tipo {@link LocalDate}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalDateConverter implements Converter<String, LocalDate>{

    /** Properties con los formatos de fecha y hora de la aplicación */
    private final TemporalFormatProperties temporalFormatProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public LocalDate convert( final String source ){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( this.temporalFormatProperties.getDate() );
        return Optional.ofNullable( source ).map( value -> LocalDate.parse( value, formatter )).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
