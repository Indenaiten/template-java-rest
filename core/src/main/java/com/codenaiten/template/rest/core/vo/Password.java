package com.codenaiten.template.rest.core.vo;

import java.io.Serial;


public class Password extends BaseValueObject<String>{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Longitud mínima */
    public static final int MIN_SIZE = 8;

    /** Longitud máxima */
    public static final int MAX_SIZE = 256;

    /** Expresión regular que define el formato válido */
    public static final String FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.@$!%*?&])[A-Za-z\\d.@$!%*?&]{8,}$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link Password} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link Password}.
     */
    public Password( final String value ){
        super( value.toLowerCase().trim() );
        if( this.value.length() < MIN_SIZE ) throw new IllegalArgumentException();
        //            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( this.value.length() > MAX_SIZE ) throw new IllegalArgumentException();
        //            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !this.value.matches( FORMAT )) throw new IllegalArgumentException();
        //            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_INVALID, FORMAT, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link Password} es válido.
     *
     * @param value {@link String} que representa el valor del {@link Password}.
     *
     * @return {@code true} si el valor del {@link Password} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new Password( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
