package com.codenaiten.template.rest.app.exception;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AppException extends RuntimeException{

    private static final AppMessage MESSAGE = AppMessage.ERROR_GENERIC;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final MessageI18n messageI18n;
    private final Object[] args;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AppException(){
        super( MESSAGE.getLoggerMessage() );
        this.messageI18n = MESSAGE;
        this.args = new String[0];
    }

    public AppException( final MessageI18n message, final Object... args ){
        super( message.getLoggerMessage().formatted( args ));
        this.messageI18n = message;
        this.args = args;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
