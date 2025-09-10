package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.shared.AppMessage;
import lombok.Getter;

@Getter
public class ConstraintException extends AppException{

    public static final AppMessage DEFAULT = AppMessage.ERROR_CONSTRAINT_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public ConstraintException( final AppMessage message, final Object... args ) {
        super( message, args );
    }

    public ConstraintException(){
        super( DEFAULT );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
