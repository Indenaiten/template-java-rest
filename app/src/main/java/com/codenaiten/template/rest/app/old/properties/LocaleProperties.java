package com.codenaiten.template.rest.app.old.properties;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/**
 * Properties que contiene las propiedades relacionadas con el idioma.
 *
 * @see Properties
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class LocaleProperties extends Properties {

    /** Idioma por defecto del sistema */
    @Value( "${app.locale.default:es}" )
    private String localeDefault;

    /** Nombre del cookie que almacena el idioma */
    @Value( "${app.locale.cookie.name:lang}" )
    private String localeCookieName;

    /** Lista de idiomas soportados por el sistema */
    private List<Locale> supportedLocales;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring y que pueden requerir dependencias que si son
     * beans de Spring, después de construir la clase.
     */
    @PostConstruct
    public void init(){
        this.supportedLocales = List.of( Locale.forLanguageTag( "es-ES" ), Locale.forLanguageTag( "en-EN" ),
                Locale.forLanguageTag( "it-IT" ), Locale.forLanguageTag( "gl-ES" ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el {@link Locale} por defecto del sistema.
     *
     * @return {@link Locale} que representa el {@link Locale} por defecto del sistema.
     */
    public Locale getLocaleDefault(){
        return Locale.forLanguageTag( this.localeDefault );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
