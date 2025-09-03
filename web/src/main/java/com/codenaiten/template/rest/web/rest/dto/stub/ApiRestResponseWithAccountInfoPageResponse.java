package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema( description = "Respuesta con datos de una página con los datos de las cuentas de usuario" )
public class ApiRestResponseWithAccountInfoPageResponse extends ApiRestResponse<List<AccountInfoResponse>> {
    public ApiRestResponseWithAccountInfoPageResponse(
            final ApiRestCodeResponse code, final String message, final Map<String, Object> matadata, final List<AccountInfoResponse> data ) {
        super( code, message, matadata, data );
    }
}
