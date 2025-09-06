package com.codenaiten.template.rest.app.old.exception;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
@Getter
public class LanguageNotSupportedException extends AppException {

    private static final MessageI18n MESSAGE = AppMessage.ERROR_LANGUAGE_VALUE_INVALID;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final Locale language;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public LanguageNotSupportedException( final Locale language ){
        super( MESSAGE, language );
        this.language = language;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
