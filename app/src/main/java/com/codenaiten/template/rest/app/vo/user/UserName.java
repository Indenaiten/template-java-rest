package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.vo.ValueObject;

public class UserName extends ValueObject<String> {

    /** Longitud mínima del nombre de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del nombre de un usuario */
    public static final int MAX_SIZE = 50;

    /** Expresión regular que define el formato válido de un nombre de usuario */
    public static final String FORMAT = "^\\p{L}(?:[\\p{L} .'-]+\\p{L})$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserName( final String value ){
        super( value );
        if( value.length() < MIN_SIZE ) throw new IllegalArgumentException( "User Name must be at least %d characters long: %s".formatted( MIN_SIZE, value ));
        if( value.length() > MAX_SIZE ) throw new IllegalArgumentException( "User Name must be at most %d characters long: %s".formatted( MAX_SIZE, value ));
        if( !value.matches( FORMAT )) throw new IllegalArgumentException( "User Name must match the format '%s': %s".formatted( FORMAT, value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final String value ){
        return test( () -> new UserName( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
