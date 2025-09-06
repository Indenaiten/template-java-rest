package com.codenaiten.template.rest.app.old.exception.security;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.exception.AppException;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SecurityException extends AppException {

    private static final AppMessage MESSAGE = AppMessage.ERROR_SECURITY_GENERIC;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public SecurityException(){
        super(MESSAGE);
    }

    public SecurityException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
