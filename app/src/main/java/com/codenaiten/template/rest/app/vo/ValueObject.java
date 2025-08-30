package com.codenaiten.template.rest.app.vo;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public abstract class ValueObject<T extends Serializable> implements Serializable {

    @EqualsAndHashCode.Include
    protected T value;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @JsonCreator
    protected ValueObject( final T value ){
        if( Objects.isNull( value )) throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_VALUE_REQUIRED );
        this.value = value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @JsonValue
    public T value() {
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    protected static <T> boolean test( final Supplier<T> supplier ) {
        try{
            supplier.get();
            return true;
        }
        catch( final Exception e ){
            return false;
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| COMPARISON |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean different( final Object obj ) {
        return !this.equals( obj );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| TO STRING |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String toString() {
        return String.valueOf( this.value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
