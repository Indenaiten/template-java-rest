package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema( description = "Respuesta con datos de un usuario" )
public class ApiRestResponseWithUserInfoResponse extends ApiRestResponse<UserInfoResponse> {
    public ApiRestResponseWithUserInfoResponse(
            final ApiRestCodeResponse code, final String message, final Map<String, Object> metadata, final UserInfoResponse data ) {
        super( code, message, metadata, data );
    }
}
