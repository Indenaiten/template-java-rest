package com.codenaiten.template.rest.core.feature.account.vo;

import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.vo.BaseValueObject;
import com.codenaiten.template.rest.core.shared.vo.ValueObject;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Language implements ValueObject<Locale>{

    ES_ES( Locale.forLanguageTag( "es_ES" )),
    EN_EN( Locale.forLanguageTag( "en_EN" ));

//--------------------------------------------------------------------------------------------------------------------\\

    private final Locale value;

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Locale value(){
        return this.value;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| BUILDER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public static Language of( final Locale value ){
        return Stream.of( values() )
                .filter( lang -> Objects.equals( lang.value(), value ))
                .findFirst()
                .orElseThrow( AppException::new );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    public static Language of( final String value ){
        final Locale locale = Locale.forLanguageTag( value );
        return Stream.of( values() )
                .filter( lang -> Objects.equals( lang.value(), locale ))
                .findFirst()
                .orElseThrow( AppException::new );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link Language} es válido.
     *
     * @param value {@link Locale} que representa el valor del {@link Language}.
     *
     * @return {@code true} si el valor del {@link Language} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final Locale value ){
        return BaseValueObject.test( () -> Language.of( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor de un {@link Language} es válido.
     *
     * @param value {@link String} que representa el valor del {@link Language}.
     *
     * @return {@code true} si el valor del {@link Language} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return BaseValueObject.test( () -> Language.of( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
