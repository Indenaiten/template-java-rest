package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Respuesta con datos de la cuenta de usuario" )
public class ApiRestResponseWithAccountInfoResponse extends ApiRestResponse<AccountInfoResponse> {
    public ApiRestResponseWithAccountInfoResponse( final ApiRestCodeResponse code, final String message, final AccountInfoResponse data ) {
        super( code, message, data );
    }
}
