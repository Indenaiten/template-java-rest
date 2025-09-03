package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.ValueObject;

/**
 * Value Object que representa el apellido de un {@link User}.
 *
 * @see ValueObject
 * @see String
 */
public class UserSurname extends ValueObject<String> {

    /** Longitud mínima del apellido de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del apellido de un usuario */
    public static final int MAX_SIZE = 50;

    /** Expresión regular que define el formato válido de un apellido de usuario */
    public static final String FORMAT = "^\\p{L}(?:[\\p{L} .'-]+\\p{L})$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link UserSurname} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link UserSurname}.
     */
    public UserSurname( final String value ){
        super( value );
        if( value.length() < MIN_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( value.length() > MAX_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !value.matches( FORMAT ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_SURNAME_VALUE_INVALID, FORMAT, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link UserSurname} es valido.
     *
     * @param value {@link String} que representa el valor del {@link UserSurname}.
     *
     * @return {@code true} si el valor del {@link UserSurname} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new UserSurname( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
