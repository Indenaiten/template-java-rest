package com.codenaiten.template.rest.app.api;

import java.util.List;
import java.util.Locale;

/**
 * Service con los casos de uso relacionados con la configuración del sistema.
 */
public interface ConfigurationService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza el idioma del sistema.
     *
     * @param lang {@link Locale} que representa el idioma a establecer en el sistema.
     */
    void lang( Locale lang );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el idioma actual configurado en el sistema.
     *
     * @return {@link Locale} que representa el idioma actual configurado en el sistema.
     */
    Locale getLang();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la lista de idiomas soportados por el sistema.
     *
     * @return {@link List} de {@link Locale} que representan los idiomas soportados por el sistema.
     */
    List<Locale> getSupportedLanguages();

// ------------------------------------------------------------------------------------------------------------------ \\

}
