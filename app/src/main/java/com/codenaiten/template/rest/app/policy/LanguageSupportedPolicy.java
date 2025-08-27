package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.exception.LanguageNotSupportedException;
import com.codenaiten.template.rest.app.properties.LocaleProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class LanguageSupportedPolicy {

    private final LocaleProperties localeProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public void check( final Locale language ){
        final List<Locale> supportedLocales = this.localeProperties.getSupportedLocales();
        if( Objects.isNull( language ) || supportedLocales.stream().noneMatch( supported -> equals( language, supported )))
            throw new LanguageNotSupportedException( language );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private static boolean equals( final Locale lang, final Locale supportedLang ){
        if( Objects.isNull( lang ) || Objects.isNull( supportedLang )) return false;
        return lang.toLanguageTag().equalsIgnoreCase( supportedLang.getLanguage() ) || lang.equals( supportedLang );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
