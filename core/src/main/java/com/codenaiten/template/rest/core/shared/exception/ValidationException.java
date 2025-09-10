package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.shared.AppMessage;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ValidationException extends AppException{

    public static final AppMessage DEFAULT = AppMessage.ERROR_VALIDATION_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\

    private final List<ConstraintException> exceptions;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public ValidationException( final ConstraintException... exception ){
        super( DEFAULT );
        this.exceptions = List.of( exception );
    }

    public ValidationException( final List<ConstraintException> exceptions ){
        super( DEFAULT );
        this.exceptions = exceptions;
    }

    public ValidationException( final AppMessage message, final Object... args ) {
        super( message, args );
        this.exceptions = Collections.emptyList();
    }

    public ValidationException(){
        super( DEFAULT );
        this.exceptions = Collections.emptyList();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
