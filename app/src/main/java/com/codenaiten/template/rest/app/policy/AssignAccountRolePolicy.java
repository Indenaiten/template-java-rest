package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Policy que obtiene el correspondiente {@link AccountRole} para asignar a una {@link Account} según las políticas
 * definidas.
 */
@Slf4j
@RequiredArgsConstructor
public class AssignAccountRolePolicy {

    /** Repository relacionado con las entidades de tipo {@link Account} */
    private final AccountRepository accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Recupera el {@link AccountRole} para asignar a una {@link Account} según el valor proporcionado.
     *
     * @param role {@link AccountRole} que representa el valor del rol de la {@link Account}.
     *
     * @return {@link AccountRole#USER} si el valor del {@link AccountRole} proporciona es {@code null}; Si no existen
     * cuentas creadas todavía devuelve {@link AccountRole#ADMIN}.
     */
    public AccountRole get( AccountRole role ){
        if( this.accountRepository.count() == 0 ) role = AccountRole.ADMIN;
        return Optional.ofNullable( role ).orElse( AccountRole.USER );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
