package com.codenaiten.template.rest.app.mapper;

import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class, TemporalMapper.class, UserMapper.class })
public interface AccountMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ENTITY ---> RESULT |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    AccountInfoResult toInfoResult( Account account );

// ------------------------------------------------------------------------------------------------------------------ \\

}
