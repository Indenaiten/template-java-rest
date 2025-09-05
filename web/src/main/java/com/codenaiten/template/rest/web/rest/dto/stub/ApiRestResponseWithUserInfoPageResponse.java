package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema( description = "Respuesta con datos de una página con los datos de los usuario" )
public class ApiRestResponseWithUserInfoPageResponse extends ApiRestResponse<List<UserInfoResponse>> {
    public ApiRestResponseWithUserInfoPageResponse(
            final ApiRestCodeResponse code,
            final String message,
            final Map<String, Object> metadata,
            final List<UserInfoResponse> data
    ){
        super( code, message, metadata, data );
    }
}
