package com.codenaiten.template.rest.core.shared.validator;

import com.codenaiten.template.rest.core.shared.exception.ConstraintException;

import java.util.List;

public interface Validator<T> {

//--------------------------------------------------------------------------------------------------------------------\\

    List<ConstraintException> validate( T candidate );

//--------------------------------------------------------------------------------------------------------------------\\

}
