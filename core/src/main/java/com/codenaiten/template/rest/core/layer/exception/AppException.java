package com.codenaiten.template.rest.core.layer.exception;

import com.codenaiten.template.rest.core.layer.AppMessage;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public static final AppMessage DEFAULT = AppMessage.ERROR_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\

    private final AppMessage appMessage;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public AppException( final AppMessage message, final Object... args ) {
        super( message.getMessage().formatted( args ));
        this.appMessage = message;
    }

    public AppException() {
        super( DEFAULT.getMessage() );
        this.appMessage = DEFAULT;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
