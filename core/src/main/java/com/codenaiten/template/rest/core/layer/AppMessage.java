package com.codenaiten.template.rest.core.layer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AppMessage {

    ERROR_GENERIC( "error.generic", "An unexpected error has occurred" );

//--------------------------------------------------------------------------------------------------------------------\\

    private final String key;
    private final String message;

//--------------------------------------------------------------------------------------------------------------------\\
//---| BUILDER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public static AppMessage of(final String key ){
        return Stream.of( values() )
                .filter( value -> value.getKey().equalsIgnoreCase( key ))
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException( "Invalid Message key: %s".formatted( key )));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
