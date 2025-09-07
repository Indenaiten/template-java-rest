package com.codenaiten.template.rest.core.shared.vo.common;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.old.exception.validation.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;

/**
 * Value Object que representa un correo electrónico.
 *
 * @see BaseValueObject
 * @see String
 */
public class Email extends BaseValueObject<String> {

    /** Longitud mínima del correo electrónico */
    public static final int MIN_SIZE = 6;

    /** Longitud máxima del correo electrónico */
    public static final int MAX_SIZE = 256;

    /** Expresión regular que define el formato válido de un correo electrónico */
    public static final String FORMAT = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link Email} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link Email}.
     */
    public Email( final String value ){
        super( value );
        if( value.length() < MIN_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_EMAIL_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( value.length() > MAX_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_EMAIL_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !value.matches( FORMAT ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_EMAIL_VALUE_INVALID, FORMAT, value);
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link Email} es válido.
     *
     * @param value {@link String} que representa el valor del {@link Email}.
     *
     * @return {@code true} si el valor del {@link Email} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new Email( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
