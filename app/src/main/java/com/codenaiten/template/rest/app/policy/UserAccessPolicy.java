package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.security.access.CreateAccessException;
import com.codenaiten.template.rest.app.spec.access.user.UserWriteSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserAccessPolicy {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public void checkWrite( final Account requester, final User user ){
        if( new UserWriteSpec( user ).not().test( requester ))
            throw new CreateAccessException( AppMessage.ERROR_SECURITY_USER_WRITE_NOT_ALLOWED, requester.getId(), user.getId() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
