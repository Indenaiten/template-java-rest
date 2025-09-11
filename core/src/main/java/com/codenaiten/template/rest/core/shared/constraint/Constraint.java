package com.codenaiten.template.rest.core.shared.constraint;

import com.codenaiten.template.rest.core.shared.exception.ConstraintException;

import java.util.Optional;

public interface Constraint<T> {

//--------------------------------------------------------------------------------------------------------------------\\

    Optional<ConstraintViolation<?>> check( T candidate );

//--------------------------------------------------------------------------------------------------------------------\\
//---| DEFAULT METHODS |----------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    default void validate( T candidate ) throws ConstraintException {
        this.check( candidate ).orElseThrow( ConstraintException::new );
    }

    default boolean test( final T candidate ){
        return this.check( candidate ).isEmpty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
