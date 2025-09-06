package com.codenaiten.template.rest.app.old.exception.security.access;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class DeleteAccessException extends AccessException {

    private static final AppMessage MESSAGE = AppMessage.ERROR_SECURITY_DELETE_NOT_ALLOWED;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public DeleteAccessException(){
        super( MESSAGE );
    }

    public DeleteAccessException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
