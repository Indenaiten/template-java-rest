package com.codenaiten.template.rest.app.vo.account;

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
        if( value.length() < MIN_SIZE ) throw new IllegalArgumentException( "Account password must be at least %d characters long: %s".formatted( MIN_SIZE, value ));
        if( value.length() > MAX_SIZE ) throw new IllegalArgumentException( "Account password must be at most %d characters long: %s".formatted( MAX_SIZE, value ));
        if( !value.matches( FORMAT )) throw new IllegalArgumentException( "Account password must match the format '%s': %s".formatted( FORMAT, value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final String value ){
        return test( () -> new AccountPassword( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
