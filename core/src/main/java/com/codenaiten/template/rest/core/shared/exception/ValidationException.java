package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.shared.AppMessageKey;
import lombok.Getter;

@Getter
public class ValidationException extends LocalizedException {

    public static final AppMessageKey DEFAULT = AppMessageKey.ERROR_VALIDATION_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public ValidationException( final AppMessageKey messageKey, final Object... args ){
        super( messageKey, args );
    }

    public ValidationException(){
        super( DEFAULT );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
