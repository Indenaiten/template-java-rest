package com.codenaiten.template.rest.app.spec.access.account;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.spec.Specification;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Specification que permite comprobar si se tiene permiso para acceder a la información de los recursos de tipo
 * {@link Account}.
 *
 * @see Specification
 * @see Account
 */
@Slf4j
@RequiredArgsConstructor
public class AccountReadSpec implements Specification<Account> {

    /** {@link Account} a la que se requiere acceder */
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
