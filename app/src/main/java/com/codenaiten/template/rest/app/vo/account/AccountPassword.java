package com.codenaiten.template.rest.app.vo.account;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.BaseValueObject;

/**
 * Value Object que representa la contraseña de una {@link Account}.
 *
 * @see BaseValueObject
 * @see String
 */
public class AccountPassword extends BaseValueObject<String> {

    /** Longitud mínima de la contraseña de la cuenta de un usuario */
    public static final int MIN_SIZE = 8;

    /** Longitud máxima de la contraseña de la cuenta de un usuario */
    public static final int MAX_SIZE = 256;

    /** Expresión regular que define el formato válido de una contraseña de una cuenta de usuario */
    public static final String FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.@$!%*?&])[A-Za-z\\d.@$!%*?&]{8,}$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link AccountPassword} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link AccountPassword}.
     */
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

    /**
     * Valida si el valor de un {@link AccountPassword} es valido.
     *
     * @param value {@link String} que representa el valor del {@link AccountPassword}.
     *
     * @return {@code true} si el valor del {@link AccountPassword} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new AccountPassword( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
