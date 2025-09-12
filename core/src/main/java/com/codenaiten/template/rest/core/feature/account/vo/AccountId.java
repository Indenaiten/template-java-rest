package com.codenaiten.template.rest.core.feature.account.vo;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serial;
import java.util.UUID;

public class AccountId extends BaseValueObject<UUID>{

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link AccountId} a partir de un {@link UUID}.
     *
     * @param value {@link UUID} que representa el valor del {@link AccountId}.
     */
    public AccountId( final UUID value ){
        super( value );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| BUILDER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Crea un nuevo {@link AccountId} aleatorio.
     *
     * @return {@link AccountId} que representa un identificador aleatorio.
     */
    public static AccountId random(){
        final UUID id = UUID.randomUUID();
        return new AccountId( id );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Crea un nuevo {@link AccountId} a partir de un {@link String} válido.
     *
     * <li>El valor <strong>NO</strong> es {@code null}</li>
     * <li>El valor <strong>NO</strong> es un {@link UUID}</li>
     *
     * @param value {@link String} que representa el valor del {@link AccountId}.
     *
     * @throws ValidationException si el valor del {@link String} no es un {@link UUID} válido.
     *
     * @return {@link AccountId} que representa el valor del {@link AccountId}.
     */
    @JsonCreator
    public static AccountId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new ValidationException( e, CoreMessageKey.ERROR_VALIDATION_ACCOUNT_VO_ACCOUNT_ID_INVALID, value );
        }
        return new AccountId( id );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link AccountId} es válido.
     *
     * @param value {@link UUID} que representa el valor del {@link AccountId}.
     *
     * @return {@code true} si el valor del {@link AccountId} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final UUID value ){
        return test( () -> new AccountId( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link AccountId} es válido.
     *
     * @param value {@link String} que representa el valor del {@link AccountId}.
     *
     * @return {@code true} si el valor del {@link AccountId} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> AccountId.of( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
