package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.feature.user.dto.UserPrivateInfo;
import com.codenaiten.template.rest.core.feature.user.dto.UserPublicInfo;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;

public interface UserService {

//--------------------------------------------------------------------------------------------------------------------\\

    UserPublicInfo getPublicInfo( UserId userId );

//--------------------------------------------------------------------------------------------------------------------\\

    UserPrivateInfo getPrivateInfo( UserId userId );

//--------------------------------------------------------------------------------------------------------------------\\

}
