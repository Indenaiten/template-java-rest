package com.codenaiten.template.rest.app.exception.security;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.AppException;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
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
