package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.CoreMessageKey;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public static final CoreMessageKey DEFAULT = CoreMessageKey.ERROR_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public AppException( final String message ){
        super( message );
    }

    public AppException(){
        super( DEFAULT.getMessage() );
    }

    public AppException( final Throwable cause, final String message ){
        super( message, cause );
    }

    public AppException( final Throwable cause ){
        super( DEFAULT.getMessage(), cause );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
