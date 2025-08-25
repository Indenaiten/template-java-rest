package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.command.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.RefreshTokenCommand;
import com.codenaiten.template.rest.app.dto.command.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.dto.result.RefreshTokenResult;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.web.rest.dto.request.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RefreshTokenRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.dto.response.RefreshTokenResponse;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class, TemporalMapper.class })
public interface AuthenticationWebMapper {

    LoginCommand toCommand( LoginRequest request );
    RegisterCommand toCommand( RegisterRequest request );
    RefreshTokenCommand toCommand( RefreshTokenRequest request );

    LoginResponse toResponse( LoginResult result );
    RefreshTokenResponse toResponse( RefreshTokenResult result );

}
