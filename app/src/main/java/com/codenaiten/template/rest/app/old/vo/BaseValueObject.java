package com.codenaiten.template.rest.app.old.vo;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.exception.validation.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Clase base para los {@link BaseValueObject} que representan un valor de un tipo {@link Serializable}.
 *
 * @param <T> {@link T} que extiende de {@link Serializable} que representa el valor del {@link BaseValueObject}.
 */
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public abstract class BaseValueObject<T extends Serializable> implements ValueObject<T>, Serializable {

    /**
     * Obtiene el valor del {@link BaseValueObject}.
     *
     * @return {@link T} que extiende de {@link Serializable} que representa el valor del {@link BaseValueObject}.
     */
    @EqualsAndHashCode.Include
    protected T value;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que recibe el valor del {@link BaseValueObject}.
     *
     * @param value {@link T} que extiende de {@link Serializable} que representa el valor del {@link BaseValueObject}.
     */
    @JsonCreator
    protected BaseValueObject(final T value ){
        if( Objects.isNull( value )) throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_VALUE_REQUIRED );
        this.value = value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el valor del {@link BaseValueObject}.
     *
     * @return {@link T} que extiende de {@link Serializable} que representa el valor del {@link BaseValueObject}.
     */
    @Override
    @JsonValue
    public T value() {
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Helper que valida si el valor de un {@link BaseValueObject} es valido.
     *
     * @param supplier {@link Supplier} que representa la funcion que se va a ejecutar para validar el valor del
     *                 {@link BaseValueObject}.
     *
     * @return {@code true} si el valor del {@link BaseValueObject} es valido, {@code false} en caso contrario.
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
     * Obtiene el valor del {@link BaseValueObject} en formato {@link String}.
     *
     * @return {@link String} que representa el valor del {@link BaseValueObject}.
     */
    @Override
    public String toString() {
        return String.valueOf( this.value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
