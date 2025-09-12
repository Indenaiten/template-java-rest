package com.codenaiten.template.rest.core.feature.account.constraint;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.constraint.violation.InvalidAccountOwnerConstraintViolation;
import com.codenaiten.template.rest.core.feature.account.constraint.violation.NotFoundAccountOwnerConstraintViolation;
import com.codenaiten.template.rest.core.feature.account.port.AccountRepository;
import com.codenaiten.template.rest.core.feature.user.port.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountOwnerConstraint implements Constraint<Account> {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final Account candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "Account candidate to check AccountOwnerConstraint is required" );

        final UserId id = candidate.getOwner();
        if( !this.userRepository.exists( id )) return Optional.of( new NotFoundAccountOwnerConstraintViolation( candidate ));
        if( this.accountRepository.exists( id )) return Optional.of( new InvalidAccountOwnerConstraintViolation( candidate ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
