package com.codenaiten.template.rest.app.vo;

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
        if( value.length() < MIN_SIZE ) throw new IllegalArgumentException( "Email must be at least %d characters long: %s".formatted( MIN_SIZE, value ));
        if( value.length() > MAX_SIZE ) throw new IllegalArgumentException( "Email must be at most %d characters long: %s".formatted( MAX_SIZE, value ));
        if( !value.matches( FORMAT )) throw new IllegalArgumentException( "Email must match the format '%s': %s".formatted( FORMAT, value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final String value ){
        return test( () -> new Email( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
