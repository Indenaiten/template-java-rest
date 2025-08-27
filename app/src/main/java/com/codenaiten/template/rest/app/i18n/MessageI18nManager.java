package com.codenaiten.template.rest.app.i18n;

import org.springframework.boot.logging.LogLevel;

public interface MessageI18nManager{

    String getMessage( MessageI18n messageI18n, Object... args );
    String getMessageAndLogger( MessageI18n messageI18n, LogLevel level, Object... args );
    String getMessage( String key, Object... args );
}
