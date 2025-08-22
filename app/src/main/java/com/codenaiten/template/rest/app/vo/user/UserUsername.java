package com.codenaiten.template.rest.app.vo.user;

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
        if( value.length() < MIN_SIZE ) throw new IllegalArgumentException( "User Username must be at least %d characters long: %s".formatted( MIN_SIZE, value ));
        if( value.length() > MAX_SIZE ) throw new IllegalArgumentException( "User Username must be at most %d characters long: %s".formatted( MAX_SIZE, value ));
        if( !value.matches( FORMAT )) throw new IllegalArgumentException( "User Username must match the format '%s': %s".formatted( FORMAT, value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final String value ){
        return test( () -> new UserUsername( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
