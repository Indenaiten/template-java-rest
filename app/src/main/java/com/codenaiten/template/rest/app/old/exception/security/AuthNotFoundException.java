package com.codenaiten.template.rest.app.old.exception.security;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AuthNotFoundException extends SecurityException {

    private static final MessageI18n MESSAGE = AppMessage.ERROR_SECURITY_AUTH_NOT_FOUND;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AuthNotFoundException(){
        super( MESSAGE );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
