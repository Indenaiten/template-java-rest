package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.command.account.AccountCreateCommand;
import com.codenaiten.template.rest.app.dto.command.account.AccountUpdateCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.mapper.AccountMapper;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.web.rest.dto.request.AccountCreateRequest;
import com.codenaiten.template.rest.web.rest.dto.request.AccountUpdateRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class, TemporalMapper.class, AccountMapper.class, UserWebMapper.class })
public interface AccountWebMapper {

    AccountCreateCommand toCommand( AccountCreateRequest request );
    AccountUpdateCommand toCommand( AccountUpdateRequest request );

    AccountInfoResponse toResponse( AccountInfoResult result );
}
