package com.codenaiten.template.rest.app.i18n;

import org.springframework.boot.logging.LogLevel;

import java.lang.reflect.Array;

/**
 * Manager que permite obtener los mensajes traducidos del sistema.
 */
public interface MessageI18nManager{

    /**
     * Obtiene un mensaje traducido del sistema a partir de un {@link MessageI18n}.
     *
     * @param messageI18n {@link MessageI18n} que representa el mensaje i18n del sistema.
     * @param args Un {@link Array} de {@link Object} que representan los argumentos que se deben incluir en el mensaje
     *             i18n.
     *
     * @return {@link String} con el mensaje traducido del sistema a partir del {@link MessageI18n}.
     */
    String getMessage( MessageI18n messageI18n, Object... args );

    /**
     * Obtiene un mensaje traducido del sistema a partir de un {@link MessageI18n} y hace un log con el mensaje de log
     * del {@link MessageI18n} a partir del nivel de log indicado.
     *
     * @param messageI18n {@link MessageI18n} que representa el mensaje i18n del sistema.
     * @param level {@link LogLevel} que representa el nivel de log que se desea hacer en el log.
     * @param args Un {@link Array} de {@link Object} que representan los argumentos que se deben incluir en el mensaje
     *             i18n.
     *
     * @return {@link String} con el mensaje traducido del sistema a partir del {@link MessageI18n}.
     */
    String getMessageAndLogger( MessageI18n messageI18n, LogLevel level, Object... args );

    /**
     * Obtiene un mensaje traducido del sistema a partir de una clave i18n.
     *
     * @param key {@link String} que representa la clave i18n del mensaje.
     * @param args Un {@link Array} de {@link Object} que representan los argumentos que se deben incluir en el mensaje
     *
     * @return {@link String} con el mensaje traducido del sistema a partir de la clave proporcionada.
     */
    String getMessage( String key, Object... args );
}
