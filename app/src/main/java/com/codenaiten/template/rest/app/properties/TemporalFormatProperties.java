package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties que contiene las propiedades relacionadas con los formatos temporales.
 *
 * @see Properties
 */
@Slf4j
@Getter
@Component
@ConfigurationProperties( prefix = "app.temporal.format" )
public class TemporalFormatProperties extends Properties {

    /** Formato de fecha del sistema */
    private String date = "dd/MM/yyyy";

    /** Formato de hora del sistema */
    private String time = "HH:mm:ss";

    /** Formato de fecha y hora del sistema */
    private String dateTime = "%s %s".formatted( this.date, this.time );

}
