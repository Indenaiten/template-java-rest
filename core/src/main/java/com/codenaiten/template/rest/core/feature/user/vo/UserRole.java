package com.codenaiten.template.rest.core.feature.user.vo;

import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;
import com.codenaiten.template.rest.core.shared.vo.ValueObject;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum UserRole implements ValueObject<Integer>{

    ADMIN( 1 ),
    USER( 2 );

//--------------------------------------------------------------------------------------------------------------------\\

    private final Integer value;

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Integer value(){
        return this.value;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| BUILDER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public static UserRole of(final Integer value ){
        return Stream.of( values() )
                .filter( role -> Objects.equals( role.value(), value ))
                .findFirst()
                .orElseThrow( AppException::new );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link UserRole} es válido.
     *
     * @param value {@link Integer} que representa el valor del {@link UserRole}.
     *
     * @return {@code true} si el valor del {@link UserRole} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final Integer value ){
        return BaseValueObject.test( () -> UserRole.of( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
