package com.codenaiten.template.rest.core.shared.vo;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;

import java.io.Serial;

public class EncodedPassword extends BaseValueObject<String>{

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link EncodedPassword} a partir de un {@link String} válido.
     *
     * <li>El valor <strong>NO</strong> es {@code null}</li>
     * <li>El valor <strong>NO</strong> esta vacío/li>
     *
     * @param value {@link String} que representa el valor del {@link EncodedPassword}.
     *
     * @throws ValidationException Si el valor del {@link EncodedPassword} no es válido.
     */
    public EncodedPassword( final String value ){
        super( value.trim() );
        if( this.value.isEmpty() ) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_VO_ENCODED_PASSWORD_EMPTY );
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
