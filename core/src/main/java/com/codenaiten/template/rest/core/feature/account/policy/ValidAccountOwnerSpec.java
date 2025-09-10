package com.codenaiten.template.rest.core.feature.account.policy;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.spi.AccountRepository;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.policy.Policy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidAccountOwnerSpec implements Policy<Account> {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public void check( final Account candidate ){
        if( Objects.isNull( candidate )) throw new IllegalArgumentException( "Account to check valid owner is required" );
        if( !this.userRepository.exists( candidate.getOwner() )) throw new AppException();
        this.accountRepository.find( candidate.getOwner() ).orElseThrow( AppException::new );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
