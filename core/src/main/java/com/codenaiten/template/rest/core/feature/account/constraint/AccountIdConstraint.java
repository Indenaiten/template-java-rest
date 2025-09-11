package com.codenaiten.template.rest.core.feature.account.constraint;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.constraint.violation.AlreadyExistsAccountIdConstraintViolation;
import com.codenaiten.template.rest.core.feature.account.spi.AccountRepository;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class AccountIdConstraint implements Constraint<Account> {

    private final AccountRepository accountRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public AccountIdConstraint( final AccountRepository accountRepository ){
        log.info( "AccountIdConstraint initialized" );

        if( Objects.isNull( accountRepository ))
            throw new IllegalArgumentException( "AccountRepository is required by AccountIdConstraint" );

        this.accountRepository = accountRepository;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final Account candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "Account candidate to check AccountIdConstraint is required" );

        final AccountId id = candidate.getId();
        if( this.accountRepository.exists( id )) return Optional.of( new AlreadyExistsAccountIdConstraintViolation( candidate ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
