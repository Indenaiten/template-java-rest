package com.codenaiten.template.rest.boot.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class I18nConfig{

    @Value( "${app.i18n.language.default:es}" )
    private String languageDefault;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init() {
        Locale.setDefault( Locale.forLanguageTag( this.languageDefault ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
