package com.codenaiten.template.rest.app.old.vo.image;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.entity.Image;
import com.codenaiten.template.rest.app.old.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.old.vo.BaseValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;

/**
 * Value Object que representa un identificador de un {@link Image}.
 *
 * @see BaseValueObject
 * @see UUID
 */
public class ImageId extends BaseValueObject<UUID> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link ImageId} a partir de un {@link UUID}.
     *
     * @param value {@link UUID} que representa el valor del {@link ImageId}.
     */
    public ImageId( final UUID value ){
        super( value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Crea un nuevo {@link ImageId} aleatorio.
     *
     * @return {@link ImageId} que representa un identificador aleatorio.
     */
    public static ImageId random(){
        final UUID id = UUID.randomUUID();
        return new ImageId( id );
    }

    /**
     * Crea un nuevo {@link ImageId} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link ImageId}.
     *
     * @return {@link ImageId} que representa el valor del {@link ImageId}.
     */
    @JsonCreator
    public static ImageId of(final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_IMAGE_ID_VALUE_INVALID, value );
        }
        return new ImageId( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link ImageId} es valido.
     *
     * @param value {@link UUID} que representa el valor del {@link ImageId}.
     *
     * @return {@code true} si el valor del {@link ImageId} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final UUID value ){
        return test( () -> new ImageId( value ));
    }

    /**
     * Valida si el valor de un {@link ImageId} es valido.
     *
     * @param value {@link String} que representa el valor del {@link ImageId}.
     *
     * @return {@code true} si el valor del {@link ImageId} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> ImageId.of( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
