package com.codenaiten.template.rest.core.feature.user.vo;

import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serial;
import java.util.UUID;

public class UserId extends BaseValueObject<UUID> {

    @Serial
    private static final long serialVersionUID = 1L;

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
            throw new IllegalArgumentException( "El valor del UserId no es válido" );
        }
        return new UserId( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link UserId} es válido.
     *
     * @param value {@link UUID} que representa el valor del {@link UserId}.
     *
     * @return {@code true} si el valor del {@link UserId} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final UUID value ){
        return test( () -> new UserId( value ));
    }

    /**
     * Valida si el valor de un {@link UserId} es válido.
     *
     * @param value {@link String} que representa el valor del {@link UserId}.
     *
     * @return {@code true} si el valor del {@link UserId} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> UserId.of( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
