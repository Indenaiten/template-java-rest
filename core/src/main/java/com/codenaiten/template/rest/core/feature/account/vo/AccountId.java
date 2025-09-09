package com.codenaiten.template.rest.core.feature.account.vo;

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
     * Crea un nuevo {@link AccountId} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link AccountId}.
     *
     * @return {@link AccountId} que representa el valor del {@link AccountId}.
     */
    @JsonCreator
    public static AccountId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new IllegalArgumentException( "El valor del AccountId no es válido" );
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
