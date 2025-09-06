package com.codenaiten.template.rest.app.old.exception;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class InvalidAccessTokenException extends InvalidTokenException {

    private static final MessageI18n MESSAGE = AppMessage.ERROR_SECURITY_AUTH_INVALID_ACCESS_TOKEN;
// ------------------------------------------------------------------------------------------------------------------ \\

    private final String token;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public InvalidAccessTokenException( final String token ){
        super( MESSAGE, token );
        this.token = token;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
