package com.codenaiten.template.rest.app.old.exception.security.access;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class WriteAccessException extends AccessException {

    private static final AppMessage MESSAGE = AppMessage.ERROR_SECURITY_WRITE_NOT_ALLOWED;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public WriteAccessException(){
        super( MESSAGE );
    }

    public WriteAccessException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
