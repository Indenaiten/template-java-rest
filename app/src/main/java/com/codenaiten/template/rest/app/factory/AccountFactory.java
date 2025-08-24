package com.codenaiten.template.rest.app.factory;

import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class AccountFactory {

    private final AccountRepository accountRepository;
    private final PasswordEncoderManager passwordEncoderManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Factory create( final User owner, final Email email, final AccountPassword password ) {
        //Check Required fields
        if( Objects.isNull( owner ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_OWNER_REQUIRED );
        if( Objects.isNull( email ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_EMAIL_REQUIRED );
        if( Objects.isNull( password ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_PASSWORD_REQUIRED );

        return new Factory( owner, email.value(), password.value() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @RequiredArgsConstructor
    public class Factory {
        private final User owner;
        private final String email;
        private final String password;

        public Account build(){
            //Check if username is unique
            if( AccountFactory.this.accountRepository.existsByEmail( this.email ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_EMAIL_ALREADY_EXISTS, this.email );

            //Encode password
            final String hashedPassword = AccountFactory.this.passwordEncoderManager.hash( this.password );

            final AccountId id = AccountId.random();
            final Timestamp now = Timestamp.now();
            return Account.builder().id( id.value() ).owner( this.owner ).email( this.email ).password( hashedPassword )
                    .createdAt( now.value() ).updatedAt( now.value() ).build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
