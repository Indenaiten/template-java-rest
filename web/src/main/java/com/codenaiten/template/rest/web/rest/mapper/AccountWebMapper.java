package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.web.rest.dto.request.account.CreateAccountRequest;
import com.codenaiten.template.rest.web.rest.dto.request.account.FilterAccountRequest;
import com.codenaiten.template.rest.web.rest.dto.request.account.UpdateAccountRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class, TemporalMapper.class, UserWebMapper.class })
public interface AccountWebMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| REQUEST ---> COMMAND |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    FilterAccountCommand toCommand( FilterAccountRequest result );
    CreateAccountCommand toCommand( CreateAccountRequest request );
    UpdateAccountCommand toCommand( UpdateAccountRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| RESULT ---> RESPONSE |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    AccountInfoResponse toResponse( AccountInfoResult result );

// ------------------------------------------------------------------------------------------------------------------ \\

}
