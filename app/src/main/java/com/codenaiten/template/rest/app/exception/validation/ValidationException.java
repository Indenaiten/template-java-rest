package com.codenaiten.template.rest.app.exception.validation;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.AppException;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ValidationException extends AppException {

    private static final AppMessage MESSAGE = AppMessage.ERROR_VALIDATION_GENERIC;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public ValidationException(){
        super(MESSAGE);
    }

    public ValidationException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
