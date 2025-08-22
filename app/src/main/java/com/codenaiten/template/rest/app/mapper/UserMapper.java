package com.codenaiten.template.rest.app.mapper;

import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.entity.User;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = { OptionalMapper.class })
public interface UserMapper {

    UserInfoResult toInfoResult( User user );
}
