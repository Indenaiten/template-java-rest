package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
@ConfigurationProperties( prefix = "app.temporal.format" )
public class TemporalFormatProperties extends Properties {

    private String date = "dd/MM/yyyy";
    private String time = "HH:mm:ss";
    private String dateTime = "%s %s".formatted( this.date, this.time );

}
