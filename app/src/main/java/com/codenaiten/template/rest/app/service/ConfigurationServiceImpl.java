package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.ConfigurationService;
import com.codenaiten.template.rest.app.policy.LanguageSupportedPolicy;
import com.codenaiten.template.rest.app.properties.LocaleProperties;
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

    private final LocaleProperties localeProperties;

// ------------------------------------------------------------------------------------------------------------------ \\

    private LanguageSupportedPolicy languageSupportedPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init(){
        this.languageSupportedPolicy = new LanguageSupportedPolicy( this.localeProperties );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void lang( final Locale lang ){
        this.languageSupportedPolicy.check( lang );
        LocaleContextHolder.setLocale( lang );
    }

    @Override
    public Locale getLang() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public List<Locale> getSupportedLanguages() {
        return this.localeProperties.getSupportedLocales();
    }


// ------------------------------------------------------------------------------------------------------------------ \\

}
