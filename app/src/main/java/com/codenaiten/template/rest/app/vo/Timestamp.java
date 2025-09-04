package com.codenaiten.template.rest.app.vo;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;

import java.time.LocalDateTime;

/**
 * Value Object que representa una fecha y hora de un momento determinado.
 *
 * @see BaseValueObject
 * @see LocalDateTime
 */
public class Timestamp extends BaseValueObject<LocalDateTime> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link Timestamp} a partir de un {@link LocalDateTime}.
     *
     * @param value {@link LocalDateTime} que representa el valor del {@link Timestamp}.
     */
    public Timestamp( final LocalDateTime value ){
        super( value );
        if( value.isAfter( LocalDateTime.now() ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_TIMESTAMP_VALUE_FUTURE, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor del {@link Timestamp} es valido.
     *
     * @param value {@link LocalDateTime} que representa el valor del {@link Timestamp}.
     *
     * @return {@code true} si el valor del {@link Timestamp} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final LocalDateTime value ){
        return test( () -> new Timestamp( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene un nuevo {@link Timestamp} con la fecha y hora actual.
     *
     * @return {@link Timestamp} con la fecha y hora actual.
     */
    public static Timestamp now(){
        return new Timestamp( LocalDateTime.now() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
