package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.web.rest.dto.request.user.FilterUserRequest;
import com.codenaiten.template.rest.web.rest.dto.request.user.UpdateUserRequest;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class, TemporalMapper.class })
public interface UserWebMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| REQUEST ---> COMMAND |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    FilterUserCommand toCommand( FilterUserRequest result );
    UpdateUserCommand toCommand( UpdateUserRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| RESULT ---> RESPONSE |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    UserInfoResponse toResponse( UserInfoResult result );

// ------------------------------------------------------------------------------------------------------------------ \\

}
