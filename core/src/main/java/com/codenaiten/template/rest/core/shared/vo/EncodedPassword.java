package com.codenaiten.template.rest.core.shared.vo;

import java.io.Serial;

public class EncodedPassword extends BaseValueObject<String>{

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link EncodedPassword} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link EncodedPassword}.
     */
    public EncodedPassword( final String value ){
        super( value.trim() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link EncodedPassword} es válido.
     *
     * @param value {@link String} que representa el valor del {@link EncodedPassword}.
     *
     * @return {@code true} si el valor del {@link EncodedPassword} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new EncodedPassword( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
