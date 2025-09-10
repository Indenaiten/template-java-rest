package com.codenaiten.template.rest.core.shared.validator;

import com.codenaiten.template.rest.core.shared.exception.ConstraintException;

import java.util.Optional;

public interface Constraint<T> {

//--------------------------------------------------------------------------------------------------------------------\\

    Optional<ConstraintException> check( T candidate );

//--------------------------------------------------------------------------------------------------------------------\\

}
