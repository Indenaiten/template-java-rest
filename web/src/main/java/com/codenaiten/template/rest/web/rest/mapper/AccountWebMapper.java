package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.mapper.AccountMapper;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class, TemporalMapper.class, AccountMapper.class, UserWebMapper.class })
public interface AccountWebMapper {

    AccountInfoResponse toResponse( AccountInfoResult result );
}
