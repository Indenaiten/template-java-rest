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
@ApiResponse( responseCode = "401",
              description = "Token inválido o ausente, se requiere autenticación",
              content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema( implementation = ApiRestResponseEmptyResponse.class ),
                                  examples = @ExampleObject( name = "Unauthorized",
                                                                     summary = "Ejemplo de error 401",
                                                                     description = "Cuando falta o es incorrecta la autenticación",
                                                                     value = "{ \"code\": 3000, \"message\": \"Unauthorized\", \"metadata\": { \"info\": { \"timestamp\": 1756817000505 }}, \"data\": null }"
                                  )
              )
)
@ApiResponse( responseCode = "403",
              description = "Acceso denegado, faltan permisos",
              content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema( implementation = ApiRestResponseEmptyResponse.class ),
                                  examples = @ExampleObject( name = "Access Denied",
                                                             summary = "Ejemplo de error 403",
                                                             description = "Cuando faltan permisos para realizar alguna acción en el servidor",
                                                             value = "{ \"code\": 3020, \"message\": \"Access Denied\", \"metadata\": { \"info\": { \"timestamp\": 1756817000505 }}, \"data\": null }"
                                  )
              )
)
public @interface AuthenticationErrors {
}
