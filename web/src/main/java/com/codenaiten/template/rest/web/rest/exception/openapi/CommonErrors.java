package com.codenaiten.template.rest.web.rest.exception.openapi;

import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseEmptyResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention( RetentionPolicy.RUNTIME )
@Documented
@ApiResponse( responseCode = "400",
              description = "Solicitud incorrecta, petición o datos inválidos",
              content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema( implementation = ApiRestResponseEmptyResponse.class ),
                                  examples = @ExampleObject( name = "Bad Request",
                                                             summary = "Ejemplo de error 400",
                                                             description = "Cuando la petición o los datos no son válidos",
                                                             value = "{ \"code\": 5000, \"message\": \"Bad Request\", \"metadata\": { \"info\": { \"timestamp\": 1756817000505 }}, \"data\": null }"
                                  )
              )
)
@ApiResponse( responseCode = "404",
              description = "Recurso no encontrado",
              content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema( implementation = ApiRestResponseEmptyResponse.class ),
                                  examples = @ExampleObject( name = "Not Found",
                                                             summary = "Ejemplo de error 404",
                                                             description = "Resource Not Found",
                                                             value = "{ \"code\": 4000, \"message\": \"Descripción del error\", \"metadata\": { \"info\": { \"timestamp\": 1756817000505 }}, \"data\": null }"
                                  )
              )
)
@ApiResponse( responseCode = "500",
              description = "Error interno en el servidor",
              content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema( implementation = ApiRestResponseEmptyResponse.class ),
                                  examples = @ExampleObject( name = "Internal Server Error",
                                                             summary = "Ejemplo de error 500",
                                                             description = "Cuando ocurre un error genérico en el servidor",
                                                             value = "{ \"code\": 2000, \"message\": \"Internal Server Error\", \"metadata\": { \"info\": { \"timestamp\": 1756817000505 }}, \"data\": null }"
                                  )
              )
)
public @interface CommonErrors {
}
