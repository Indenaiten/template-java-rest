package com.codenaiten.template.rest.app.old.mapper;

import com.codenaiten.template.rest.app.old.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.old.entity.Account;
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
