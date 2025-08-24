package com.codenaiten.template.rest.app.exception.auth;

import com.codenaiten.template.rest.app.exception.SecurityException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AuthNotFoundException extends SecurityException{

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AuthNotFoundException(){
        super( AppMessage.ERROR_SECURITY_AUTH_NOT_FOUND );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
