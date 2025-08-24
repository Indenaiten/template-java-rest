package com.codenaiten.template.rest.app.exception.auth;

import com.codenaiten.template.rest.app.exception.SecurityException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class InvalidTokenException extends SecurityException{

    private final String token;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public InvalidTokenException( final String token ){
        super( AppMessage.ERROR_SECURITY_AUTH_INVALID_TOKEN, token );
        this.token = token;
    }

    public InvalidTokenException( final MessageI18n message, final String token ){
        super( message, token );
        this.token = token;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
