package com.codenaiten.template.rest.app.api;

import java.util.List;
import java.util.Locale;

/**
 * Service con los casos de uso relacionados con las configuraciones del sistema.
 */
public interface ConfigurationService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Establece un idioma soportado en el sistema.
     *
     * @param lang {@link Locale} que representa el idioma a establecer en el sistema.
     */
    void lang( Locale lang );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el idioma actual establecido en el sistema.
     *
     * @return {@link Locale} que representa el idioma actual establecido en el sistema.
     */
    Locale getLang();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene una lista con los idiomas soportados en el sistema.
     *
     * @return {@link List} con los {@link Locale} que representan los idiomas soportados en el sistema.
     */
    List<Locale> getSupportedLanguages();

// ------------------------------------------------------------------------------------------------------------------ \\

}
