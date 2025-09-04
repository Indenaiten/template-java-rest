package com.codenaiten.template.rest.app.vo.image;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.BaseValueObject;
import com.codenaiten.template.rest.app.vo.ValueObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Value Object que representa un tipo de contenido (Content-Type) en formato String de una imagen.
 *
 * @see BaseValueObject
 * @see String
 */
@Getter
@RequiredArgsConstructor
public enum ImageContentType implements ValueObject<String> {

    IMAGE_JPG( "image/jpg", "jpg" ),
    IMAGE_JPEG( "image/jpeg", "jpeg" ),
    IMAGE_PNG( "image/png", "png" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private final String value;
    private final String extension;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String value() {
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static ImageContentType of(final String value ){
        for( final ImageContentType imageContentType : ImageContentType.values() ){
            if( imageContentType.value.equalsIgnoreCase( value )) return imageContentType;
        }
        throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_IMAGE_CONTENT_TYPE_VALUE_INVALID, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link ImageContentType} es valido.
     *
     * @param value {@link String} que representa el valor del {@link ImageContentType}.
     *
     * @return {@code true} si el valor del {@link ImageContentType} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return BaseValueObject.test( () -> ImageContentType.valueOf( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
