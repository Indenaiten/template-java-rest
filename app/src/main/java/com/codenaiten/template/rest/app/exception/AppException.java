package com.codenaiten.template.rest.app.exception;

import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AppException extends RuntimeException{

    private static final AppMessage MESSAGE_DEFAULT = AppMessage.ERROR_GENERIC;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final MessageI18n messageI18n;
    private final Object[] args;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AppException(){
        super( MESSAGE_DEFAULT.getLoggerMessage() );
        this.messageI18n = MESSAGE_DEFAULT;
        this.args = new String[0];
    }

    public AppException( final MessageI18n message, final Object... args ){
        super( message.getLoggerMessage().formatted( args ));
        this.messageI18n = message;
        this.args = args;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
