package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.vo.ValueObject;

public class UserSurname extends ValueObject<String> {

    /** Longitud mínima del apellido de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del apellido de un usuario */
    public static final int MAX_SIZE = 50;

    /** Expresión regular que define el formato válido de un apellido de usuario */
    public static final String FORMAT = "^\\p{L}(?:[\\p{L} .'-]+\\p{L})$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserSurname( final String value ){
        super( value );
        if( value.length() < MIN_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( value.length() > MAX_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !value.matches( FORMAT ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_SURNAME_VALUE_INVALID, FORMAT, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final String value ){
        return test( () -> new UserSurname( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
