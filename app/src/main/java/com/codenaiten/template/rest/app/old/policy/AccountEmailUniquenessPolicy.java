package com.codenaiten.template.rest.app.old.policy;

import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.exception.AccountEmailAlreadyException;
import com.codenaiten.template.rest.app.old.repository.AccountRepository;
import com.codenaiten.template.rest.app.old.spec.AccountEmailUniquenessSpec;
import com.codenaiten.template.rest.app.old.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Policy que permite validar la unicidad de un {@link Email} en una {@link Account}.
 */
@Slf4j
@RequiredArgsConstructor
public class AccountEmailUniquenessPolicy {

    /** Repository relacionado con las entidades de tipo {@link Account} */
    private final AccountRepository accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Comprueba si el {@link Email} de la {@link Account} proporcionado no está registrado todavía.
     *
     * @param email {@link Email} de la {@link Account} que se va a evaluar.
     *
     * @throws AccountEmailAlreadyException si el {@link Email} ya está registrado en otra {@link Account}.
     *
     * @see AccountEmailUniquenessSpec
     */
    public void check( final Email email ){
        if( new AccountEmailUniquenessSpec( this.accountRepository ).not().test( email )){
            throw new AccountEmailAlreadyException( email );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
