package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.web.rest.dto.request.auth.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.auth.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class, TemporalMapper.class })
public interface AuthenticationWebMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| REQUEST ---> COMMAND |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    LoginCommand toCommand( LoginRequest request );
    RegisterCommand toCommand( RegisterRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| RESULT ---> RESPONSE |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    LoginResponse toResponse( LoginResult result );

// ------------------------------------------------------------------------------------------------------------------ \\

}
