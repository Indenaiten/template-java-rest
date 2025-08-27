package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.command.user.UserUpdateCommand;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.app.mapper.UserMapper;
import com.codenaiten.template.rest.web.rest.dto.request.UserUpdateRequest;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class, TemporalMapper.class, UserMapper.class })
public interface UserWebMapper {

    UserUpdateCommand toCommand( UserUpdateRequest request );

    UserInfoResponse toResponse( UserInfoResult result );
}
