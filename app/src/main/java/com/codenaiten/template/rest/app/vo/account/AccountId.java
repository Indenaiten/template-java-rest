package com.codenaiten.template.rest.app.vo.account;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.BaseValueObject;

import java.util.UUID;

/**
 * Value Object que representa un identificador de un {@link Account}.
 *
 * @see BaseValueObject
 * @see UUID
 */
public class AccountId extends BaseValueObject<UUID> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link AccountId} a partir de un {@link UUID}.
     *
     * @param value {@link UUID} que representa el valor del {@link AccountId}.
     */
    public AccountId( final UUID value ){
        super( value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Crea un nuevo {@link AccountId} aleatorio.
     *
     * @return {@link AccountId} que representa un identificador aleatorio.
     */
    public static AccountId random(){
        final UUID id = UUID.randomUUID();
        return new AccountId( id );
    }

    /**
     * Crea un nuevo {@link AccountId} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link AccountId}.
     *
     * @return {@link AccountId} que representa el valor del {@link AccountId}.
     */
    public static AccountId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_ACCOUNT_ID_VALUE_INVALID, value );
        }
        return new AccountId( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link AccountId} es valido.
     *
     * @param value {@link UUID} que representa el valor del {@link AccountId}.
     *
     * @return {@code true} si el valor del {@link AccountId} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final UUID value ){
        return test( () -> new AccountId( value ));
    }

    /**
     * Valida si el valor de un {@link AccountId} es valido.
     *
     * @param value {@link String} que representa el valor del {@link AccountId}.
     *
     * @return {@code true} si el valor del {@link AccountId} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> AccountId.of( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
