package com.codenaiten.template.rest.web.rest.dto;

import com.codenaiten.template.rest.app.old.dto.result.PageResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Getter
@Schema( description = "Respuesta estándar de la API" )
public class ApiRestResponse<T>{

    @Schema( description = "Indica el código de respuesta de la API", example = "0" )
    private final ApiRestCodeResponse code;

    @Schema( description = "Mensaje de estado o error", example = "Operación completada con éxito" )
    private final String message;

    @Schema( description = "Metadatos adicionales que acompañan a la respuesta (paginación, flags, etc.)",
            example = "{ \"info\": { \"timestamp\": 1756817000505 }, \"page\": { \"number\": 0, \"size\": 25, \"total\": 100, \"pages\": 4, \"next\": true, \"previous\": false }}"
    )
    private final Map<String, Object> metadata;

    @Schema( description = "Resultado de la operación" )
    private final T data;


// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public ApiRestResponse( final ApiRestCodeResponse code, final String message, final Map<String, Object> metadata, final T data ){
        this.code = code;
        this.message = message;
        this.metadata = Optional.ofNullable( metadata ).orElse( new HashMap<>() );
        this.data = data;
        final Map<String, Object> info = new HashMap<>();
        info.put( "timestamp", System.currentTimeMillis() );
        this.metadata.put( "info", info );
    }


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
        private Map<String, Object> metadata = new HashMap<>();

        public ApiResponseBuilder code( final ApiRestCodeResponse code ){
            this.code = code;
            return this;
        }

        public ApiResponseBuilder message( final String message ){
            this.message = message;
            return this;
        }

        public ApiResponseBuilder metadata( final Map<String, Object> metadata ){
            this.metadata = metadata;
            return this;
        }

        public ApiResponseBuilder addMetadata( final Map<String, Object> metadata ){
            this.metadata.putAll( metadata );
            return this;
        }

        public ApiResponseBuilder page( final PageResult<?> data ){
            final Map<String, Object> page = new HashMap<>();
            page.put( "number", data.page() );
            page.put( "size", data.size() );
            page.put( "total", data.total() );
            page.put( "pages", data.totalPages() );
            page.put( "next", data.page() < data.totalPages() - 1 );
            page.put( "previous", data.page() > 0 && data.totalPages() > 1 );
            this.metadata.put( "page", page );
            return this;
        }

        public ApiRestResponse<Empty> build(){
            return new ApiRestResponse<>( code, message, this.metadata, null );
        }

        public <T> ApiRestResponse<T> build( final T data ){
            return new ApiRestResponse<>( this.code, this.message, this.metadata, data );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
