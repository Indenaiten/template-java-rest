package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Respuesta sin datos" )
public class ApiRestResponseEmptyResponse extends ApiRestResponse<Empty> {
    public ApiRestResponseEmptyResponse( final ApiRestCodeResponse code, final String message ) {
        super( code, message, null );
    }
}
