package com.codenaiten.template.rest.app.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper( componentModel = "spring" )
public interface TemporalMapper {

    default Long toEpochMilli( final LocalDateTime dateTime ){
        return dateTime.atZone( ZoneId.systemDefault() ).toInstant().toEpochMilli();
    }

}
