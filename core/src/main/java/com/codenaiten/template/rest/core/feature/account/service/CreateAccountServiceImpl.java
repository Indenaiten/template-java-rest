package com.codenaiten.template.rest.core.feature.account.service;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.account.vo.Language;
import com.codenaiten.template.rest.core.feature.user.spi.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAccountServiceImpl {

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Account create( final Input input ){
        log.info( "Creating account: {}", input );

        // Get Data
        final AccountId id = AccountId.random();
        final UserId owner = input.getOwner();
        final Language lang = input.getLang().orElse( null );
        final Timestamp createdAt = Timestamp.now();
        final Timestamp updatedAt = null;

        // Create Account
        final Account account = new Account( id, owner, lang, createdAt, updatedAt );

        // Check
        if( !this.userRepository.exists( owner )) throw new AppException();

        // Return Account
        return account;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| INPUT |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Getter
    @Setter
    @RequiredArgsConstructor
    class Input {

        private final UserId owner;
        private Language lang;

    //----------------------------------------------------------------------------------------------------------------\\
    //---| GETTERS |--------------------------------------------------------------------------------------------------\\
    //----------------------------------------------------------------------------------------------------------------\\

        public Optional<Language> getLang() {
            return Optional.ofNullable( this.lang );
        }

    //----------------------------------------------------------------------------------------------------------------\\

    }

//--------------------------------------------------------------------------------------------------------------------\\

}
