package com.codenaiten.template.rest.app.old.vo.user;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.old.vo.BaseValueObject;

/**
 * Value Object que representa el nombre de un {@link User}.
 *
 * @see BaseValueObject
 * @see String
 */
public class UserName extends BaseValueObject<String> {

    /** Longitud mínima del nombre de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del nombre de un usuario */
    public static final int MAX_SIZE = 50;

    /** Expresión regular que define el formato válido de un nombre de usuario */
    public static final String FORMAT = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+(?:[ '-][A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link UserName} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link UserName}.
     */
    public UserName( final String value ){
        super( value );
        if( value.length() < MIN_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_NAME_VALUE_MIN_SIZE, MIN_SIZE, value );
        if( value.length() > MAX_SIZE )
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_NAME_VALUE_MAX_SIZE, MAX_SIZE, value );
        if( !value.matches( FORMAT ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_NAME_VALUE_INVALID, FORMAT, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link UserName} es valido.
     *
     * @param value {@link String} que representa el valor del {@link UserName}.
     *
     * @return {@code true} si el valor del {@link UserName} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new UserName( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
