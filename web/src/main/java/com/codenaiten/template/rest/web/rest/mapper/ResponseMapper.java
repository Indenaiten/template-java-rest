package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.old.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.old.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.old.dto.result.LoginResult;
import com.codenaiten.template.rest.app.old.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.old.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.old.mapper.TemporalMapper;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.ImageInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class, TemporalMapper.class })
public interface ResponseMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| AUTHENTICATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    LoginResponse toResponse( LoginResult result );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMAGE |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    ImageInfoResponse toResponse( ImageInfoResult result );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ACCOUNT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    AccountInfoResponse toResponse( AccountInfoResult result );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| USER |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    UserInfoResponse toResponse( UserInfoResult result );

// ------------------------------------------------------------------------------------------------------------------ \\

}
