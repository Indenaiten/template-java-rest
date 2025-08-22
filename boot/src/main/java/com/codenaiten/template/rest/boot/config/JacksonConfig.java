package com.codenaiten.template.rest.boot.config;

import com.codenaiten.template.rest.app.properties.TemporalFormatProperties;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JacksonConfig {

    private final TemporalFormatProperties temporalFormatProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BEANS |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonDateCustomizer() {
        return builder -> {
            final String dateFormat = this.temporalFormatProperties.getDate();
            final String dateTimeFormat = this.temporalFormatProperties.getDateTime();
            final String timeFormat = this.temporalFormatProperties.getTime();

            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern( dateFormat );
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( dateTimeFormat );
            final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern( timeFormat );

            builder.serializers( new LocalDateSerializer( dateFormatter ),
                                 new LocalDateTimeSerializer( dateTimeFormatter ),
                                 new LocalTimeSerializer( timeFormatter ));

            builder.deserializers( new LocalDateDeserializer( dateFormatter ),
                                   new LocalDateTimeDeserializer( dateTimeFormatter ),
                                   new LocalTimeDeserializer( timeFormatter ));

            builder.simpleDateFormat( dateFormat );
        };
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

