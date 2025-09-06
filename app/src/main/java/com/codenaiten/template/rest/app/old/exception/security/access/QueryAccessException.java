package com.codenaiten.template.rest.app.old.exception.security.access;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class QueryAccessException extends AccessException {

    private static final AppMessage MESSAGE = AppMessage.ERROR_SECURITY_QUERY_NOT_ALLOWED;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public QueryAccessException(){
        super( MESSAGE );
    }

    public QueryAccessException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
