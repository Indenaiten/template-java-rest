package com.codenaiten.template.rest.core.feature.user.vo;

import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;

import java.io.Serial;
import java.util.Objects;

public class UserSurname extends BaseValueObject<String>{

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\

    /** Longitud mínima del apellido de un usuario */
    public static final int MIN_SIZE = 3;

    /** Longitud máxima del apellido de un usuario */
    public static final int MAX_SIZE = 50;

    /** Expresión regular que define el formato válido de un apellido de usuario */
    public static final String FORMAT = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+(?:[ '-][A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$";

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link UserSurname} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link UserSurname}.
     */
    public UserSurname( final String value ){
        super( value.trim() );
        if( this.value.length() < MIN_SIZE ) throw new AppException();
        if( this.value.length() > MAX_SIZE ) throw new AppException();
        if( !this.value.matches( FORMAT )) throw new AppException();
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| COMPARISON |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public boolean equals( final Object obj ){
        if( Objects.isNull( obj ) || getClass() != obj.getClass() ) return false;
        final UserSurname that = (UserSurname) obj;
        return Objects.equals( this.value().toLowerCase(), that.value().toLowerCase() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link UserSurname} es válido.
     *
     * @param value {@link String} que representa el valor del {@link UserSurname}.
     *
     * @return {@code true} si el valor del {@link UserSurname} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new UserSurname( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
