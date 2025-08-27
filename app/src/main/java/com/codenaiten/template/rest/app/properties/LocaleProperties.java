package com.codenaiten.template.rest.app.properties;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class LocaleProperties extends Properties {

    @Value( "${app.locale.default:es}" )
    private String localeDefault;

    @Value( "${app.locale.cookie.name:lang}" )
    private String localeCookieName;

    private List<Locale> supportedLocales;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init(){
        this.supportedLocales = List.of( Locale.forLanguageTag( "es-ES" ), Locale.forLanguageTag( "en-EN" ),
                Locale.forLanguageTag( "it-IT" ), Locale.forLanguageTag( "gl-ES" ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Locale getLocaleDefault(){
        return Locale.forLanguageTag( this.localeDefault );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
