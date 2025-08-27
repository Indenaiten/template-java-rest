package com.codenaiten.template.rest.app.exception;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class InvalidRefreshTokenException extends InvalidTokenException {

    private static final MessageI18n MESSAGE = AppMessage.ERROR_SECURITY_AUTH_INVALID_REFRESH_TOKEN;
// ------------------------------------------------------------------------------------------------------------------ \\

    private final String token;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public InvalidRefreshTokenException( final String token ){
        super( MESSAGE, token );
        this.token = token;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
