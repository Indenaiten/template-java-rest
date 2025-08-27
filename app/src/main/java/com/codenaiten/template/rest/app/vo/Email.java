package com.codenaiten.template.rest.app.vo;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;

public class Email extends ValueObject<String>{

    /** Longitud mínima del correo electrónico */
    public static final int MIN_SIZE = 6;

    /** Longitud máxima del correo electrónico */
    public static final int MAX_SIZE = 256;

    /** Expresión regular que define el formato válido de un correo electrónico */
    public static final String FORMAT = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

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

    public static boolean test( final String value ){
        return test( () -> new Email( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
