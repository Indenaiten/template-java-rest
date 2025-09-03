package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountRoleInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema( description = "Respuesta con una lista con los datos de los roles de las cuentas de usuario" )
public class ApiRestResponseWithAccountRoleInfoListResponse extends ApiRestResponse<List<AccountRoleInfoResponse>> {
    public ApiRestResponseWithAccountRoleInfoListResponse(
            final ApiRestCodeResponse code, final String message, final Map<String, Object> metadata, final List<AccountRoleInfoResponse> data ){
        super( code, message, metadata, data );
    }
}
