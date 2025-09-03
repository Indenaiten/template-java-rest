package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.ValueObject;

/**
 * Value Object que representa el username de un {@link User}
 *
 * @see ValueObject
 * @see String
 */
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

    /**
     * Constructor que crea un {@link UserUsername} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link UserUsername}.
     */
    public UserUsername( final String value ){
        super( value );
        this.value = value.toLowerCase().trim();
        if( this.value.length() < MIN_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( this.value.length() > MAX_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !this.value.matches( FORMAT ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_USERNAME_VALUE_INVALID, FORMAT, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link UserUsername} es valido.
     *
     * @param value {@link String} que representa el valor del {@link UserUsername}.
     *
     * @return {@code true} si el valor del {@link UserUsername} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new UserUsername( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
