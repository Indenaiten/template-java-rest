package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Respuesta con datos de una página con los datos de las cuentas de usuario" )
public class ApiRestResponseWithAccountInfoPageResponse extends ApiRestResponse<PageResponse<AccountInfoResponse>> {
    public ApiRestResponseWithAccountInfoPageResponse( final ApiRestCodeResponse code, final String message, final PageResponse<AccountInfoResponse> data ) {
        super( code, message, data );
    }
}
