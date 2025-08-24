package com.codenaiten.template.rest.app.i18n;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppMessage implements MessageI18n{

    ERROR_GENERIC( "app.error.generic", "An unexpected error has occurred" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private final String key;
    private final String loggerMessage;

// ------------------------------------------------------------------------------------------------------------------ \\

}
