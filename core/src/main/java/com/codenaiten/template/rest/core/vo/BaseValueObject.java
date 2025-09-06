package com.codenaiten.template.rest.core.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;


public abstract class BaseValueObject<T extends Serializable> implements ValueObject<T>{

    protected final T value;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @JsonCreator
    protected BaseValueObject( final T value ){
        // TODO: BaseValueObject - Añadir Validaciones
        this.value = value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @JsonValue
    public T value(){
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| COMPARISON |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean equals( final Object obj ){
        if( Objects.isNull( obj ) || getClass() != obj.getClass() ) return false;
        final ValueObject<T> that = (ValueObject<T>) obj;
        return Objects.equals( this.value(), that.value() );
    }

    @Override
    public int hashCode(){
        return Objects.hashCode( this.value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| TO STRING |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String toString() {
        return String.valueOf( this.value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Helper que valida si el valor de un {@link BaseValueObject} es válido.
     *
     * @param supplier {@link Supplier} que representa la funcion que se va a ejecutar para validar el valor del
     *                 {@link BaseValueObject}.
     *
     * @return {@code true} si el valor del {@link BaseValueObject} es válido, {@code false} en caso contrario.
     *
     * @param <T> {@link T} que extiende de {@link Serializable} que representa el valor del {@link BaseValueObject}.
     */
    public static <T> boolean test( final Supplier<T> supplier ) {
        try{
            supplier.get();
            return true;
        }
        catch( final Exception e ){
            return false;
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
