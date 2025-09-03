package com.codenaiten.template.rest.app.spec;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Specification que permite comprobar la unicidad de un {@link Email} de una {@link Account}.
 *
 * @see Specification
 * @see Email
 */
@Slf4j
@RequiredArgsConstructor
public class AccountEmailUniquenessSpec implements Specification<Email>{

    /** Repository relacionado con las entidades de tipo {@link Account} */
    private final AccountRepository accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean test( final Email candidate ){
        if( Objects.isNull( candidate )) return false;
        return !this.accountRepository.existsByEmail( candidate.value() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
