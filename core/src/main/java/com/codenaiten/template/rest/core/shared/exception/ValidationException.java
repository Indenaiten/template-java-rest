package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.CoreMessageKey;
import lombok.Getter;

@Getter
public class ValidationException extends LocalizedException {

    public static final CoreMessageKey DEFAULT = CoreMessageKey.ERROR_VALIDATION_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public ValidationException(final CoreMessageKey messageKey, final Object... args ){
        super( messageKey, args );
    }

    public ValidationException(){
        super( DEFAULT );
    }

    public ValidationException(final Throwable cause, final CoreMessageKey messageKey, final Object... args ){
        super( cause, messageKey, args );
    }

    public ValidationException( final Throwable cause ){
        super( cause, DEFAULT );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
