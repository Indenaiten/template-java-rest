package com.codenaiten.template.rest.app.old.i18n;

/**
 * Interfaz que representa un mensaje i18n del sistema.
 */
public interface MessageI18n{

    /**
     * Recupera la clave i18n del {@link MessageI18n}.
     *
     * @return {@link String} con la clave i18n del {@link MessageI18n}.
     */
    String getKey();

    /**
     * Recupera el mensaje para logs del {@link MessageI18n}.
     *
     * @return {@link String} con el mensaje para logs del {@link MessageI18n}.
     */
    String getLoggerMessage();
}
