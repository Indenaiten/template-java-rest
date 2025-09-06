package com.codenaiten.template.rest.app.old.policy;

import com.codenaiten.template.rest.app.old.exception.LanguageNotSupportedException;
import com.codenaiten.template.rest.app.old.properties.LocaleProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Policy que comprueba si un {@link Locale} es soportado por el sistema.
 */
@Slf4j
@RequiredArgsConstructor
public class LanguageSupportedPolicy {

    /** Properties con información relacionada con la configuración del lenguaje del sistema */
    private final LocaleProperties localeProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Comprueba si el {@link Locale} proporcionada es soportado por el sistema.
     *
     * @param language {@link Locale} que representa el idioma a evaluar.
     *
     * @throws LanguageNotSupportedException si el {@link Locale} proporcionado no es soportado por el sistema.
     */
    public void check( final Locale language ){
        final List<Locale> supportedLocales = this.localeProperties.getSupportedLocales();
        if( Objects.isNull( language ) || supportedLocales.stream().noneMatch( supported -> equals( language, supported )))
            throw new LanguageNotSupportedException( language );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Comprueba si dos {@link Locale} son iguales.
     *
     * @param lang {@link Locale} que representa el idioma a evaluar.
     * @param supportedLang {@link Locale} que representa el idioma soportado por el sistema.
     *
     * @return {@code true} si los {@link Locale} son iguales, {@code false} en caso contrario.
     */
    private static boolean equals( final Locale lang, final Locale supportedLang ){
        if( Objects.isNull( lang ) || Objects.isNull( supportedLang )) return false;
        return lang.toLanguageTag().equalsIgnoreCase( supportedLang.getLanguage() ) || lang.equals( supportedLang );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
