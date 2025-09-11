package com.codenaiten.template.rest.core.feature.media.vo;

import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;

import java.io.Serial;

public class MediaContentSize extends BaseValueObject<Long>{

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link MediaContentSize} a partir de un {@link Long}.
     *
     * @param value {@link Long} que representa el valor del {@link MediaContentSize}.
     */
    public MediaContentSize( final Long value ){
        super( value );
        if( this.value <= 0 ) throw new AppException();
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link MediaContentSize} es válido.
     *
     * @param value {@link Long} que representa el valor del {@link MediaContentSize}.
     *
     * @return {@code true} si el valor del {@link MediaContentSize} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final Long value ){
        return test( () -> new MediaContentSize( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
