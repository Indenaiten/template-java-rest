package com.codenaiten.template.rest.app.vo.account;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.ValueObject;

public class AccountPassword extends ValueObject<String> {

    /** Longitud mínima de la contraseña de la cuenta de un usuario */
    public static final int MIN_SIZE = 8;

    /** Longitud máxima de la contraseña de la cuenta de un usuario */
    public static final int MAX_SIZE = 256;

    /** Expresión regular que define el formato válido de una contraseña de una cuenta de usuario */
    public static final String FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.@$!%*?&])[A-Za-z\\d.@$!%*?&]{8,}$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountPassword( final String value ){
        super( value );
        if( value.length() < MIN_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( value.length() > MAX_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !value.matches( FORMAT ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_INVALID, FORMAT, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final String value ){
        return test( () -> new AccountPassword( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
