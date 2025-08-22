package com.codenaiten.template.rest.app.mapper;

import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper( componentModel = "spring" )
public interface OptionalMapper {

    default <T> T unwrap( final Optional<T> optional ){
        return optional.orElse( null );
    }

    default <T> Optional<T> wrap( final T value ){
        return Optional.ofNullable( value );
    }
}
