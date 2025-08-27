package com.codenaiten.template.rest.web.rest.exception.openapi;

import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseEmptyResponse;
import io.swagger.v3.oas.annotations.media.Content;
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
                                  schema = @Schema( implementation = ApiRestResponseEmptyResponse.class )
              )
)
@ApiResponse( responseCode = "403",
              description = "Acceso denegado, faltan permisos",
              content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema( implementation = ApiRestResponseEmptyResponse.class )
              )
)
public @interface PrivateErrors {
}
