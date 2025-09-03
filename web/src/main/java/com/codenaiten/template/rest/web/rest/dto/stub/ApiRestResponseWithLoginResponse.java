package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema( description = "Respuesta con datos del login del usuario" )
public class ApiRestResponseWithLoginResponse extends ApiRestResponse<LoginResponse> {
    public ApiRestResponseWithLoginResponse(
            final ApiRestCodeResponse code, final String message, final Map<String, Object> metadata, final LoginResponse data ) {
        super( code, message, metadata, data );
    }
}
