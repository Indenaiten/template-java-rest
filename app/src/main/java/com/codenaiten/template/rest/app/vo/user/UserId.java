package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.BaseValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;

/**
 * Value Object que representa un identificador de un {@link User}.
 *
 * @see BaseValueObject
 * @see UUID
 */
public class UserId extends BaseValueObject<UUID> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link UserId} a partir de un {@link UUID}.
     *
     * @param value {@link UUID} que representa el valor del {@link UserId}.
     */
    public UserId( final UUID value ){
        super( value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Crea un nuevo {@link UserId} aleatorio.
     *
     * @return {@link UserId} que representa un identificador aleatorio.
     */
    public static UserId random(){
        final UUID id = UUID.randomUUID();
        return new UserId( id );
    }

    /**
     * Crea un nuevo {@link UserId} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link UserId}.
     *
     * @return {@link UserId} que representa el valor del {@link UserId}.
     */
    @JsonCreator
    public static UserId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_ID_VALUE_INVALID, value );
        }
        return new UserId( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link UserId} es valido.
     *
     * @param value {@link UUID} que representa el valor del {@link UserId}.
     *
     * @return {@code true} si el valor del {@link UserId} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final UUID value ){
        return test( () -> new UserId( value ));
    }

    /**
     * Valida si el valor de un {@link UserId} es valido.
     *
     * @param value {@link String} que representa el valor del {@link UserId}.
     *
     * @return {@code true} si el valor del {@link UserId} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> UserId.of( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
