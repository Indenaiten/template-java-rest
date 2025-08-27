package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.exception.AccountEmailAlreadyException;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.spec.AccountEmailUniquenessSpec;
import com.codenaiten.template.rest.app.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AccountEmailUniquenessPolicy {

    private final AccountRepository accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public void check( final Email email ){
        if( new AccountEmailUniquenessSpec( this.accountRepository ).not().test( email )){
            throw new AccountEmailAlreadyException( email );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
