package com.codenaiten.template.rest.app.old.mapper;

import com.codenaiten.template.rest.app.old.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.old.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class, TemporalMapper.class, ImageMapper.class })
public interface UserMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ENTITY ---> RESULT |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    UserInfoResult toInfoResult( User user );

// ------------------------------------------------------------------------------------------------------------------ \\

}
