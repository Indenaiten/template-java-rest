package com.codenaiten.template.rest.app.i18n.impl;

import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageI18nManagerImpl implements MessageI18nManager{

    /** MessageSource de Spring que permite obtener mensajes de un archivo de propiedades */
    private final MessageSource messageSource;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String getMessage( final MessageI18n messageI18n, final Object... args ){
        final String key = messageI18n.getKey();
        final String loggerMessage = messageI18n.getLoggerMessage();
        log.debug( "I18N: { key: \"{}\", loggerMessage: \"{}\" args: {} }", key, loggerMessage, args );
        return getMessage( key, args );
    }

    @Override
    public String getMessageAndLogger( final MessageI18n messageI18n, final LogLevel level, final Object... args ){
        final String message = messageI18n.getLoggerMessage().formatted( args );
        switch( level ){
            case LogLevel.TRACE-> log.trace( message );
            case LogLevel.INFO -> log.info( message );
            case LogLevel.ERROR -> log.error( message );
            case LogLevel.WARN -> log.warn( message );
            default -> log.debug( message );
        }
        return this.getMessage( messageI18n, args );
    }

    @Override
    public String getMessage( final String key, final Object... args ){
        return this.messageSource.getMessage( key, args, LocaleContextHolder.getLocale() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
