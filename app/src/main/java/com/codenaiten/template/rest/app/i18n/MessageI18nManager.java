package com.codenaiten.template.rest.app.i18n;

public interface MessageI18nManager{

    String getMessage( MessageI18n messageI18n, Object... args );
    String getMessage( String key, Object... args );
}
