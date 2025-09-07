package com.codenaiten.template.rest.core.feature.user.vo;

import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;

import java.io.Serial;

public class UserUsername extends BaseValueObject<String> {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Longitud mínima del nickname de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del nickname de un usuario */
    public static final int MAX_SIZE = 25;

    /** Expresión regular que define el formato válido de un nickname de usuario */
    public static final String FORMAT = "^[a-z][a-z0-9_-]*$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link UserUsername} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link UserUsername}.
     */
    public UserUsername( final String value ){
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
     * Valida si el valor de un {@link UserUsername} es válido.
     *
     * @param value {@link String} que representa el valor del {@link UserUsername}.
     *
     * @return {@code true} si el valor del {@link UserUsername} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new UserUsername( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
