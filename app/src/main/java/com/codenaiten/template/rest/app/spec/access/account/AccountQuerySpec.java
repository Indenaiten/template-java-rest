package com.codenaiten.template.rest.app.spec.access.account;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.spec.Specification;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Specification que permite comprobar si se tiene permiso para consultar los datos de los recursos de tipo {@link Account}.
 *
 * @see Specification
 * @see Account
 */
@Slf4j
@RequiredArgsConstructor
public class AccountQuerySpec implements Specification<Account> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean test( final Account candidate ){
        if( Objects.isNull( candidate )) return false;
        return candidate.hasRole( AccountRole.ADMIN );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
