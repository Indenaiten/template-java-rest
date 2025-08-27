package com.codenaiten.template.rest.app.factory;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.policy.AccountEmailUniquenessPolicy;
import com.codenaiten.template.rest.app.policy.AssignAccountRolePolicy;
import com.codenaiten.template.rest.app.policy.LanguageSupportedPolicy;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AccountFactory {

    private final LanguageSupportedPolicy languageSupportedPolicy;
    private final AccountEmailUniquenessPolicy accountEmailUniquenessPolicy;
    private final AssignAccountRolePolicy assignAccountRolePolicy;
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

        return new Factory( owner, email.value(), password.value() ).lang( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @RequiredArgsConstructor
    public class Factory {
        private final User owner;
        private final String email;
        private final String password;
        private String lang;
        private Integer role;

        public Factory lang( final Locale lang ){
            this.lang = Optional.ofNullable( lang ).map( Locale::toLanguageTag ).orElse( null );
            return this;
        }

        public Factory role( final AccountRole role ){
            this.role = Optional.ofNullable( role ).map( AccountRole::value ).orElse( null );
            return this;
        }

        public Account build(){
            // Assign role
            AccountRole role = Optional.ofNullable( this.role ).map( AccountRole::new ).orElse( null );
            role = AccountFactory.this.assignAccountRolePolicy.get( role );

            // Check if email is unique
            AccountFactory.this.accountEmailUniquenessPolicy.check( new Email( this.email ));

            // Check Language
            this.lang = Optional.ofNullable( this.lang ).orElse( LocaleContextHolder.getLocale().getLanguage() );
            AccountFactory.this.languageSupportedPolicy.check( Locale.forLanguageTag( this.lang ));

            // Encode password
            final String hashedPassword = AccountFactory.this.passwordEncoderManager.hash( this.password );

            final AccountId id = AccountId.random();
            final Timestamp now = Timestamp.now();
            return Account.builder().id( id.value() ).owner( this.owner ).lang( this.lang ).role( role.value() )
                    .email( this.email ).password( hashedPassword ).createdAt( now.value() ).updatedAt( now.value() )
                    .build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
