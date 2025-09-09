package com.codenaiten.template.rest.core.layer.vo.media;

import com.codenaiten.template.rest.core.layer.exception.AppException;
import com.codenaiten.template.rest.core.layer.vo.BaseValueObject;
import com.codenaiten.template.rest.core.layer.vo.ValueObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum MediaContentType implements ValueObject<String>{

    IMAGE_JPG( "image/jpg", "jpg" ),
    IMAGE_JPEG( "image/jpeg", "jpeg" ),
    IMAGE_PNG( "image/png", "png" );

//--------------------------------------------------------------------------------------------------------------------\\

    private final String value;
    private final String extension;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public String value() {
        return this.value;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| BUILDER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public static MediaContentType of( final String value ){
        return Stream.of( values() )
                .filter( role -> Objects.equals( role.value(), value ))
                .findFirst()
                .orElseThrow( AppException::new );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link MediaContentType} es valido.
     *
     * @param value {@link String} que representa el valor del {@link MediaContentType}.
     *
     * @return {@code true} si el valor del {@link MediaContentType} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return BaseValueObject.test( () -> MediaContentType.of( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
