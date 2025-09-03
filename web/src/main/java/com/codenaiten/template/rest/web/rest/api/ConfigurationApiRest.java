package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseEmptyResponse;
import com.codenaiten.template.rest.web.rest.exception.openapi.CommonErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;

@CommonErrors
@Tag( name = "Configuraciones" )
@RequestMapping( "/api/config" )
public interface ConfigurationApiRest {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONFIG LANGUAGE |-------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "updateLang",
                summary = "Actualiza el lenguaje de la API",
                description = "Permite actualizar el lenguaje de la API"
    )
    @ApiResponse( responseCode = "200",
                  description = "Lenguaje actualizado correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseEmptyResponse.class )
                  )
    )
    @PostMapping( "/lang/{lang}" )
    ResponseEntity<ApiRestResponse<Empty>> updateLang(
            @Parameter( description = "Lenguaje que se va a establecer en la API", required = true )
            @PathVariable Locale lang
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| LIST SUPPORTED LANGUAGES |----------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "getLang",
                summary = "Obtiene el lenguaje actual",
                description = "Permite obtener el lenguaje actual"
    )
    @ApiResponse( responseCode = "200",
                  description = "Lenguaje actual",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseEmptyResponse.class )
                  )
    )
    @GetMapping( "/lang" )
    ResponseEntity<ApiRestResponse<String>> getLang();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| LIST SUPPORTED LANGUAGES |----------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "getSupportedLanguages",
                summary = "Obtiene la lista de lenguajes soportados",
                description = "Permite obtener una lista de los lenguajes soportados"
    )
    @ApiResponse( responseCode = "200",
                  description = "Lista de lenguajes soportados",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseEmptyResponse.class )
                  )
    )
    @GetMapping( "/lang/supported" )
    ResponseEntity<ApiRestResponse<List<String>>> getSupportedLanguages();

// ------------------------------------------------------------------------------------------------------------------ \\

}
