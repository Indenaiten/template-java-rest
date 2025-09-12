package com.codenaiten.template.rest.core.feature.user.vo;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;

import java.io.Serial;

public class UserPassword extends BaseValueObject<String> {

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\

    /** Longitud mínima */
    public static final int MIN_SIZE = 8;

    /** Longitud máxima */
    public static final int MAX_SIZE = 256;

    /** Expresión regular que define el formato válido */
    public static final String FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.@$!%*?&])[A-Za-z\\d.@$!%*?&]{8,}$";

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link UserPassword} a partir de un {@link String} válido.
     *
     * <li>El valor <strong>NO</strong> es {@code null}</li>
     * <li>El valor <strong>NO</strong> es inferior a {@link #MIN_SIZE}</li>
     * <li>El valor <strong>NO</strong> es superior a {@link #MAX_SIZE}</li>
     * <li>El valor <strong>NO</strong> coincide con el formato {@link #FORMAT}</li>
     *
     * @param value {@link String} que representa el valor del {@link UserPassword}.
     *
     * @throws ValidationException Si el valor del {@link UserPassword} no es válido.
     */
    public UserPassword(final String value ){
        super( value.trim() );
        if( this.value.length() < MIN_SIZE ) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_VO_USER_PASSWORD_MIN_SIZE, value, MIN_SIZE );
        if( this.value.length() > MAX_SIZE ) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_VO_USER_PASSWORD_MAX_SIZE, value, MAX_SIZE );
        if( !this.value.matches( FORMAT )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_VO_USER_PASSWORD_FORMAT_INVALID, value, FORMAT );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link UserPassword} es válido.
     *
     * @param value {@link String} que representa el valor del {@link UserPassword}.
     *
     * @return {@code true} si el valor del {@link UserPassword} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new UserPassword( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
