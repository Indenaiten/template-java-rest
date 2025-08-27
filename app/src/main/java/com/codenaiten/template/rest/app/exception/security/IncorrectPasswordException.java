package com.codenaiten.template.rest.app.exception.security;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class IncorrectPasswordException extends SecurityException{

    private static final MessageI18n MESSAGE = AppMessage.ERROR_SECURITY_AUTH_PASSWORD_INCORRECT;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public IncorrectPasswordException(){
        super( MESSAGE );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
