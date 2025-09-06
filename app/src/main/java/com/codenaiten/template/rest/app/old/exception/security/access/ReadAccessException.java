package com.codenaiten.template.rest.app.old.exception.security.access;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ReadAccessException extends AccessException {

    private static final AppMessage MESSAGE = AppMessage.ERROR_SECURITY_READ_NOT_ALLOWED;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public ReadAccessException(){
        super( MESSAGE );
    }

    public ReadAccessException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
