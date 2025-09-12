package com.codenaiten.template.rest.core.feature.media.vo;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;

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
     * Crea un nuevo {@link MediaId} a partir de un {@link String} válido.
     *
     * <li>El valor <strong>NO</strong> es {@code null}</li>
     * <li>El valor <strong>NO</strong> es un {@link UUID}</li>
     *
     * @param value {@link String} que representa el valor del {@link MediaId}.
     *
     * @throws ValidationException si el valor del {@link String} no es un {@link UUID} válido.
     *
     * @return {@link MediaId} que representa el valor del {@link MediaId}.
     */
    public static MediaId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new ValidationException( e, CoreMessageKey.ERROR_VALIDATION_MEDIA_VO_MEDIA_ID_INVALID, value );
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
