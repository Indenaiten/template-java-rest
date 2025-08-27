package com.codenaiten.template.rest.web.rest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum ApiRestCodeResponse {

    SUCCESS( 1000 ),
    ERROR( 2000 ),
    SECURITY_ERROR( 3000 ),
    ACCESS_TOKEN_ERROR( 3001 ),
    REFRESH_TOKEN_ERROR( 3002 ),
    ACCESS_ERROR( 3020 ),
    DATA_ERROR( 4000 ),
    VALIDATION_ERROR( 5000 );

// ------------------------------------------------------------------------------------------------------------------ \\

    private final int code;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static ApiRestCodeResponse of( final int code ){
        for( final ApiRestCodeResponse apiRestCodeResponse : values() ){
            if( apiRestCodeResponse.getCode() == code ) return apiRestCodeResponse;
        }
        throw new IllegalArgumentException( "Code %d is not valid for ApiRestCodeResponse".formatted( code ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
