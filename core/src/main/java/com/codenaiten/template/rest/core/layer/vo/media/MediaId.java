package com.codenaiten.template.rest.core.layer.vo.media;

import com.codenaiten.template.rest.core.layer.exception.AppException;
import com.codenaiten.template.rest.core.layer.vo.BaseValueObject;
import com.codenaiten.template.rest.core.layer.vo.user.UserId;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serial;
import java.util.UUID;

public class MediaId extends BaseValueObject<UUID>{

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link MediaId} a partir de un {@link UUID}.
     *
     * @param value {@link UUID} que representa el valor del {@link MediaId}.
     */
    public MediaId( final UUID value ){
        super( value );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| BUILDER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Crea un nuevo {@link MediaId} aleatorio.
     *
     * @return {@link MediaId} que representa un identificador aleatorio.
     */
    public static MediaId random(){
        final UUID id = UUID.randomUUID();
        return new MediaId( id );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Crea un nuevo {@link MediaId} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link MediaId}.
     *
     * @return {@link MediaId} que representa el valor del {@link MediaId}.
     */
    @JsonCreator
    public static MediaId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new AppException();
        }
        return new MediaId( id );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link MediaId} es válido.
     *
     * @param value {@link UUID} que representa el valor del {@link MediaId}.
     *
     * @return {@code true} si el valor del {@link MediaId} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final UUID value ){
        return test( () -> new UserId( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link MediaId} es válido.
     *
     * @param value {@link String} que representa el valor del {@link MediaId}.
     *
     * @return {@code true} si el valor del {@link MediaId} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> MediaId.of( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
