package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.PageCommand;
import com.codenaiten.template.rest.app.dto.command.user.UserUpdateCommand;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.vo.user.UserId;

public interface UserService {

    UserInfoResult me();
    UserInfoResult get( UserId id );
    PageResult<UserInfoResult> getAll( PageCommand command );
    PageResult<UserInfoResult> search( String search, PageCommand command );
    UserInfoResult update( UserId id, UserUpdateCommand command );
    UserInfoResult update( UserUpdateCommand command );
}
