package com.codenaiten.template.rest.app.old.policy;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.exception.security.access.CreateAccessException;
import com.codenaiten.template.rest.app.old.spec.access.user.UserWriteSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Policy que permite controlar el acceso a los recursos de tipo {@link User}.
 */
@Slf4j
@RequiredArgsConstructor
public class UserAccessPolicy {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite comprobar si se tiene permiso para actualizar los recursos de tipo {@link User}.
     *
     * @param requester {@link Account} que representa la cuenta de usuario que se va a evaluar si tiene permiso.
     * @param user {@link User} que representa el usuario que se va a actualizar.
     *
     * @throws CreateAccessException si no tiene permiso para actualizar el recurso.
     *
     * @see UserWriteSpec
     */
    public void checkWrite( final Account requester, final User user ){
        if( new UserWriteSpec( user ).not().test( requester ))
            throw new CreateAccessException( AppMessage.ERROR_SECURITY_USER_WRITE_NOT_ALLOWED, requester.getId(), user.getId() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
