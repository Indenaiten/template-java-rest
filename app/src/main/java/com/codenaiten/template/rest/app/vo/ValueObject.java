package com.codenaiten.template.rest.app.vo;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Clase base para los {@link ValueObject} que representan un valor de un tipo {@link Serializable}.
 *
 * @param <T> {@link T} que extiende de {@link Serializable} que representa el valor del {@link ValueObject}.
 */
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public abstract class ValueObject<T extends Serializable> implements Serializable {

    /**
     * Obtiene el valor del {@link ValueObject}.
     *
     * @return {@link T} que extiende de {@link Serializable} que representa el valor del {@link ValueObject}.
     */
    @EqualsAndHashCode.Include
    protected T value;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que recibe el valor del {@link ValueObject}.
     *
     * @param value {@link T} que extiende de {@link Serializable} que representa el valor del {@link ValueObject}.
     */
    @JsonCreator
    protected ValueObject( final T value ){
        if( Objects.isNull( value )) throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_VALUE_REQUIRED );
        this.value = value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el valor del {@link ValueObject}.
     *
     * @return {@link T} que extiende de {@link Serializable} que representa el valor del {@link ValueObject}.
     */
    @JsonValue
    public T value() {
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Helper que valida si el valor de un {@link ValueObject} es valido.
     *
     * @param supplier {@link Supplier} que representa la funcion que se va a ejecutar para validar el valor del
     *                 {@link ValueObject}.
     *
     * @return {@code true} si el valor del {@link ValueObject} es valido, {@code false} en caso contrario.
     *
     * @param <T> {@link T} que extiende de {@link Serializable} que representa el valor del {@link ValueObject}.
     */
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

    /**
     * Compara si el objeto pasado como parametro es diferente al objeto actual.
     *
     * @param obj {@link Object} que representa el objeto a comparar.
     *
     * @return {@code true} si el objeto pasado como parametro es diferente al objeto actual, {@code false} en caso
     */
    public boolean different( final Object obj ) {
        return !this.equals( obj );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| TO STRING |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el valor del {@link ValueObject} en formato {@link String}.
     *
     * @return {@link String} que representa el valor del {@link ValueObject}.
     */
    @Override
    public String toString() {
        return String.valueOf( this.value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
