package com.codenaiten.template.rest.app.old.spec.access.account;

import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.spec.Specification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Specification que permite comprobar si se tiene permiso para actualizar los recursos de tipo {@link Account}.
 *
 * @see Specification
 * @see Account
 */
@Slf4j
@RequiredArgsConstructor
public class AccountWriteSpec implements Specification<Account> {

    /** {@link Account} que se requiere actualizar */
    private final Account account;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean test( final Account candidate ){
        if( Objects.isNull( this.account ) || Objects.isNull( candidate )) return false;
        return candidate.hasRole( AccountRole.ADMIN ) || Objects.equals( this.account, candidate );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
