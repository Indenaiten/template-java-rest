package com.codenaiten.template.rest.core.shared.constraint;

import com.codenaiten.template.rest.core.CoreMessageKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ConstraintViolation<T> {

    private final T candidate;
    private final CoreMessageKey messageKey;
    private final Object[] args;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public ConstraintViolation(final T candidate, final CoreMessageKey messageKey, final Object... args ){
        this.candidate = candidate;
        this.messageKey = messageKey;
        this.args = args;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public String getMessage(){
        return this.messageKey.getMessage().formatted( this.args );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
