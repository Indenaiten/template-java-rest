package com.codenaiten.template.rest.app.exception;

import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SecurityException extends AppException{

    private static final AppMessage MESSAGE_DEFAULT = AppMessage.ERROR_SECURITY_GENERIC;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public SecurityException(){
        super( MESSAGE_DEFAULT );
    }

    public SecurityException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
