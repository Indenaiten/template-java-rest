package com.codenaiten.template.rest.core.shared.vo;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Language implements ValueObject<Locale>{

    ES_ES( Locale.forLanguageTag( "es_ES" )),
    EN_EN( Locale.forLanguageTag( "en_EN" ));

//--------------------------------------------------------------------------------------------------------------------\\

    private final @Getter( AccessLevel.NONE ) Locale value;

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
        if( Objects.isNull( value )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_VALUE_OBJECT_VALUE_NULL);
        return Stream.of( values() )
                .filter( lang -> Objects.equals( lang.value(), value ))
                .findFirst()
                .orElseThrow( () -> new ValidationException( CoreMessageKey.ERROR_VALIDATION_VO_LANGUAGE_UNSUPPORTED, value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

    public static Language of( final String value ){
        if( Objects.isNull( value )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_VALUE_OBJECT_VALUE_NULL);
        final Locale locale = Locale.forLanguageTag( value );
        return Stream.of( values() )
                .filter( lang -> Objects.equals( lang.value(), locale ))
                .findFirst()
                .orElseThrow( () -> new ValidationException( CoreMessageKey.ERROR_VALIDATION_VO_LANGUAGE_UNSUPPORTED, locale ));
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
