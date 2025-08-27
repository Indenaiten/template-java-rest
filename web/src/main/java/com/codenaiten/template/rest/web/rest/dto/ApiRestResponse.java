package com.codenaiten.template.rest.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
@Schema( description = "Respuesta estándar de la API" )
public class ApiRestResponse<T>{

    @Schema( description = "Indica el código de respuesta de la API", example = "0" )
    private final ApiRestCodeResponse code;

    @Schema( description = "Mensaje de estado o error", example = "Operación completada con éxito" )
    private final String message;

    @Schema( description = "Resultado de la operación" )
    private final T data;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @JsonProperty( "code" )
    public int getCode(){
        return this.code.getCode();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static ApiResponseBuilder builder(){
        return new ApiResponseBuilder();
    }

    public static ApiResponseBuilder success(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.SUCCESS );
    }

    public static ApiResponseBuilder error(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.ERROR );
    }

    public static ApiResponseBuilder securityError(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.SECURITY_ERROR );
    }

    public static ApiResponseBuilder accessTokenError(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.ACCESS_TOKEN_ERROR );
    }

    public static ApiResponseBuilder refreshTokenError(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.REFRESH_TOKEN_ERROR );
    }

    public static ApiResponseBuilder accessError(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.ACCESS_ERROR );
    }

    public static ApiResponseBuilder dataError(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.DATA_ERROR );
    }

    public static ApiResponseBuilder validationError(){
        return new ApiResponseBuilder().code( ApiRestCodeResponse.VALIDATION_ERROR );
    }

    public static class ApiResponseBuilder{
        private ApiRestCodeResponse code;
        private String message;

        public ApiResponseBuilder code( final ApiRestCodeResponse code ){
            this.code = code;
            return this;
        }

        public ApiResponseBuilder message( final String message ){
            this.message = message;
            return this;
        }

        public ApiRestResponse<Empty> build(){
            return new ApiRestResponse<>( code, message, null );
        }

        public <T> ApiRestResponse<T> data( final T data ){
            return new ApiRestResponse<>( code, message, data );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
