package com.codenaiten.template.rest.core.feature.user.vo;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;

import java.io.Serial;
import java.util.Objects;

public class UserName extends BaseValueObject<String>{

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\

    /** Longitud mínima del nombre de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del nombre de un usuario */
    public static final int MAX_SIZE = 50;

    /** Expresión regular que define el formato válido de un nombre de usuario */
    public static final String FORMAT = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+(?:[ '-][A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$";

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link UserName} a partir de un {@link String} válido.
     *
     * <li>El valor <strong>NO</strong> es {@code null}</li>
     * <li>El valor <strong>NO</strong> es inferior a {@link #MIN_SIZE}</li>
     * <li>El valor <strong>NO</strong> es superior a {@link #MAX_SIZE}</li>
     * <li>El valor <strong>NO</strong> coincide con el formato {@link #FORMAT}</li>
     *
     * @param value {@link String} que representa el valor del {@link UserName}.
     *
     * @throws ValidationException Si el valor del {@link UserName} no es válido.
     */
    public UserName( final String value ){
        super( value.trim() );
        if( this.value.length() < MIN_SIZE ) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_VO_USER_NAME_MIN_SIZE, value, MIN_SIZE );
        if( this.value.length() > MAX_SIZE ) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_VO_USER_NAME_MAX_SIZE, value, MAX_SIZE );
        if( !this.value.matches( FORMAT )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_VO_USER_NAME_FORMAT_INVALID, value, FORMAT );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| COMPARISON |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public boolean equals( final Object obj ){
        if( Objects.isNull( obj ) || getClass() != obj.getClass() ) return false;
        final UserName that = (UserName) obj;
        return Objects.equals( this.value().toLowerCase(), that.value().toLowerCase() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link UserName} es válido.
     *
     * @param value {@link String} que representa el valor del {@link UserName}.
     *
     * @return {@code true} si el valor del {@link UserName} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new UserName( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
