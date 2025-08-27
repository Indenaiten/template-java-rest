package com.codenaiten.template.rest.boot.config;

import com.codenaiten.template.rest.app.properties.LocaleProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LocaleConfig {

    private final LocaleProperties localeProperties;

    private String localeCookieName;
    private List<Locale> supportedLocales;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init() {
        Locale.setDefault( this.localeProperties.getLocaleDefault() );
        this.localeCookieName = this.localeProperties.getLocaleCookieName();
        this.supportedLocales = this.localeProperties.getSupportedLocales();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BEANS |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Bean
    public LocaleResolver localeResolver(){
        // Cookie
        final CookieLocaleResolver localeResolver = new CookieLocaleResolver( this.localeCookieName );
        localeResolver.setCookieMaxAge( ChronoUnit.YEARS.getDuration() );
        localeResolver.setDefaultLocale( Locale.getDefault() );

        // HEADER
        final AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setSupportedLocales( this.supportedLocales );
        acceptHeaderLocaleResolver.setDefaultLocale( Locale.getDefault() );

        // Config
        localeResolver.setDefaultLocaleFunction( acceptHeaderLocaleResolver::resolveLocale );
        return localeResolver;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
