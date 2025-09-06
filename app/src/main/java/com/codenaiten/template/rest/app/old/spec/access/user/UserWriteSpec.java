package com.codenaiten.template.rest.app.old.spec.access.user;

import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.spec.Specification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Specification que permite comprobar si se tiene permiso para actualizar los recursos de tipo {@link User}.
 *
 * @see Specification
 * @see Account
 */
@Slf4j
@RequiredArgsConstructor
public class UserWriteSpec implements Specification<Account> {

    /** {@link User} que se requiere actualizar */
    private final User user;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean test( final Account candidate ){
        if( Objects.isNull( this.user ) || Objects.isNull( candidate )) return false;
        return candidate.hasRole( AccountRole.ADMIN ) || Objects.equals( this.user, candidate.getOwner() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
