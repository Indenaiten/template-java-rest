package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.vo.ValueObject;

public class UserUsername extends ValueObject<String> {

    /** Longitud mínima del nickname de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del nickname de un usuario */
    public static final int MAX_SIZE = 25;

    /** Expresión regular que define el formato válido de un nickname de usuario */
    public static final String FORMAT = "^[a-z][a-z0-9_-]*$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserUsername( final String value ){
        super( value );
        if( value.length() < MIN_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( value.length() > MAX_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !value.matches( FORMAT ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_INVALID, FORMAT, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final String value ){
        return test( () -> new UserUsername( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
