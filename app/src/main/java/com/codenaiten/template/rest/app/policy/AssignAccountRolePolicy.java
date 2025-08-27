package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AssignAccountRolePolicy {

    private final AccountRepository accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountRole get( AccountRole role ){
        if( this.accountRepository.count() == 0 ) role = AccountRole.ADMIN;
        return Optional.ofNullable( role ).orElse( AccountRole.USER );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
