package com.codenaiten.template.rest.app.editor;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.policy.AccountEmailUniquenessPolicy;
import com.codenaiten.template.rest.app.policy.AssignAccountRolePolicy;
import com.codenaiten.template.rest.app.policy.LanguageSupportedPolicy;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
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
public class AccountEditor {

    private final LanguageSupportedPolicy languageSupportedPolicy;
    private final AccountEmailUniquenessPolicy accountEmailUniquenessPolicy;
    private final AssignAccountRolePolicy assignAccountRolePolicy;
    private final PasswordEncoderManager passwordEncoderManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Editor update( final Account account ) {
        return new Editor( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EDITOR |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public class Editor {
        private final Account account;
        private String lang;
        private Integer role;
        private String email;
        private String password;

        public Editor( final Account account ){
            this.account = account;
            this.role = account.getRole();
            this.email = account.getEmail();
            this.password = account.getPassword();
        }

        public Editor lang( Locale lang ){
            lang = Optional.ofNullable( lang ).orElse( LocaleContextHolder.getLocale() );
            AccountEditor.this.languageSupportedPolicy.check( lang );
            this.lang = lang.toLanguageTag();
            return this;
        }

        public Editor role( final AccountRole role ){
            this.role = AccountEditor.this.assignAccountRolePolicy.get( role ).value();
            return this;
        }

        public Editor email( final Email email ){
            //Check if email is null
            if( Objects.isNull( email ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_EMAIL_REQUIRED );

            //Check if username is unique
            AccountEditor.this.accountEmailUniquenessPolicy.check( email );

            this.email = email.value();
            return this;
        }

        public Editor password( final AccountPassword password ){
            //Check if password is null
            if( Objects.isNull( password ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_PASSWORD_REQUIRED );

            //Encode password
            final String hashedPassword = AccountEditor.this.passwordEncoderManager.hash( password.value() );

            this.password = hashedPassword;
            return this;
        }

        public boolean hasChanges() {
            return !Objects.equals( this.lang, this.account.getLang().orElse( null )) ||
                   !Objects.equals( this.role, this.account.getRole() ) ||
                   !Objects.equals( this.email, this.account.getEmail() ) ||
                   !Objects.equals( this.password, this.account.getPassword() );
        }

        public void apply(){
            if( this.hasChanges() ){
                this.account.setLang( this.lang );
                this.account.setRole( this.role );
                this.account.setEmail( this.email );
                this.account.setPassword( this.password );
                this.account.setUpdatedAt( Timestamp.now().value() );
            }
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
