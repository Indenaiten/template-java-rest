package com.codenaiten.template.rest.app.editor;

import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class AccountEditor {

    private final AccountRepository accountRepository;
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

    @RequiredArgsConstructor
    public class Editor {
        private final Account account;
        private String email;
        private String password;

        public Editor email( final Email email ){
            //Check if email is null
            if( Objects.isNull( email )) throw new IllegalArgumentException( "Email cannot be null" );
            final String value = email.value();

            //Check if username is unique
            if( AccountEditor.this.accountRepository.existsByEmail( value ))
                throw new IllegalArgumentException( "Account email already exists: %s".formatted( value ));

            this.email = value;
            return this;
        }

        public Editor password( final AccountPassword password ){
            //Check if password is null
            if( Objects.isNull( password )) throw new IllegalArgumentException( "Password cannot be null" );

            //Encode password
            final String hashedPassword = AccountEditor.this.passwordEncoderManager.hash( password.value() );

            this.password = hashedPassword;
            return this;
        }

        public boolean hasChanges() {
            return !Objects.equals( this.email, this.account.getEmail() ) ||
                   !Objects.equals( this.password, this.account.getPassword() );
        }

        public void apply(){
            if( this.hasChanges() ){
                this.account.setEmail( this.email );
                this.account.setPassword( this.password );
                this.account.setUpdatedAt( Timestamp.now().value() );
            }
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
