package com.codenaiten.template.rest.app.old.service;

import com.codenaiten.template.rest.app.old.api.ConfigurationService;
import com.codenaiten.template.rest.app.old.policy.LanguageSupportedPolicy;
import com.codenaiten.template.rest.app.old.properties.LocaleProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService{

    /** Properties con información relacionada con los detalles del lenguaje del sistema */
    private final LocaleProperties localeProperties;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Policy que comprueba si un lenguaje es soportado */
    private LanguageSupportedPolicy languageSupportedPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring.
     */
    @PostConstruct
    public void init(){
        // LanguageSupportedPolicy
        this.languageSupportedPolicy = new LanguageSupportedPolicy( this.localeProperties );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void lang( final Locale lang ){
        // Step 01: Check if Language is supported
        this.languageSupportedPolicy.check( lang );

        // Step 02: Set new Locale in LocaleContextHolder
        LocaleContextHolder.setLocale( lang );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public Locale getLang(){
        // Step 01: Get Current Locale
        return LocaleContextHolder.getLocale();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public List<Locale> getSupportedLanguages(){
        // Step 01: Get Supported Locales
        return this.localeProperties.getSupportedLocales();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
