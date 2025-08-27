package com.codenaiten.template.rest.web.rest.dto.stub;

import com.codenaiten.template.rest.web.rest.dto.ApiRestCodeResponse;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.PageResponse;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Respuesta con datos de una página con los datos de los usuario" )
public class ApiRestResponseWithUserInfoPageResponse extends ApiRestResponse<PageResponse<UserInfoResponse>> {
    public ApiRestResponseWithUserInfoPageResponse( final ApiRestCodeResponse code, final String message, final PageResponse<UserInfoResponse> data ) {
        super( code, message, data );
    }
}
