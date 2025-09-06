package com.codenaiten.template.rest.app.old.factory;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.old.policy.AccountEmailUniquenessPolicy;
import com.codenaiten.template.rest.app.old.policy.AssignAccountRolePolicy;
import com.codenaiten.template.rest.app.old.policy.LanguageSupportedPolicy;
import com.codenaiten.template.rest.app.old.vo.Email;
import com.codenaiten.template.rest.app.old.vo.Timestamp;
import com.codenaiten.template.rest.app.old.vo.account.AccountId;
import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Factory que permite crear objetos de tipo {@link Account}.
 */
@Slf4j
@RequiredArgsConstructor
public class AccountFactory {

    /** Policy que comprueba si un lenguaje es soportado */
    private final LanguageSupportedPolicy languageSupportedPolicy;

    /** Policy que comprueba si un {@link Email} de una {@link Account} es único */
    private final AccountEmailUniquenessPolicy accountEmailUniquenessPolicy;

    /** Policy que devuelve un {@link AccountRole} en función de unas reglas */
    private final AssignAccountRolePolicy assignAccountRolePolicy;

    /** Manager relacionado con las operaciones relacionadas con el cifrado de contraseñas */
    private final PasswordEncoderManager passwordEncoderManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite crear un objeto de tipo {@link Account} a partir de los datos obligatorios proporcionados.
     *
     * @param owner {@link User} que representa la información del propietario de la {@link Account}.
     * @param email {@link Email} que representa el correo electrónico de la {@link Account}.
     * @param password {@link AccountPassword} que representa la contraseña de la {@link Account}.
     *
     * @return {@link Factory} con los datos proporcionados de la {@link Account}.
     */
    public Factory create( final User owner, final Email email, final AccountPassword password ) {
        // Step 01 - Check Required fields
        if( Objects.isNull( owner ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_OWNER_REQUIRED );
        if( Objects.isNull( email ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_EMAIL_REQUIRED );
        if( Objects.isNull( password ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_PASSWORD_REQUIRED );

        // Step 02 - Return Factory
        return new Factory( owner, email.value(), password.value() ).lang( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clase que permite crear un objeto de tipo {@link Account} con un patrón Builder.
     */
    @RequiredArgsConstructor
    public class Factory {

        /** Información requerida del propietario de la {@link Account} */
        private final User owner;

        /** Información requerida del {@link Email} de la {@link Account} */
        private final String email;

        /** Información requerida de la {@link AccountPassword} de la {@link Account} */
        private final String password;

    // -------------------------------------------------------------------------------------------------------------- \\

        /** Información opcional del {@link Locale} de la {@link Account} */
        private String lang;

        /** Información opcional del {@link AccountRole} de la {@link Account} */
        private Integer role;

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite asignar el {@link Locale} de la {@link Account}.
         *
         * @param lang {@link Locale} que representa el lenguaje de la {@link Account}.
         *
         * @return {@link Factory} con el {@link Locale} asignado.
         */
        public Factory lang( final Locale lang ){
            this.lang = Optional.ofNullable( lang ).map( Locale::toLanguageTag ).orElse( null );
            return this;
        }

        /**
         * Permite asignar el {@link AccountRole} de la {@link Account}.
         *
         * @param role {@link AccountRole} que representa el role de la {@link Account}.
         *
         * @return {@link Factory} con el {@link AccountRole} asignado.
         */
        public Factory role( final AccountRole role ){
            this.role = Optional.ofNullable( role ).map( AccountRole::value ).orElse( null );
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite crear un objeto de tipo {@link Account} a partir de los datos proporcionados en el {@link Factory}.
         *
         * @return {@link Account} creado a partir de los datos proporcionados en el {@link Factory}.
         */
        public Account build(){
            // Step 01: Assign role
            AccountRole role = Optional.ofNullable( this.role ).map( AccountRole::new ).orElse( null );
            role = AccountFactory.this.assignAccountRolePolicy.get( role );

            // Step 02: Check if email is unique
            AccountFactory.this.accountEmailUniquenessPolicy.check( new Email( this.email ));

            // Step 03: Check Language account if is supported
            this.lang = Optional.ofNullable( this.lang ).orElse( LocaleContextHolder.getLocale().getLanguage() );
            AccountFactory.this.languageSupportedPolicy.check( Locale.forLanguageTag( this.lang ));

            // Step 04: Encode password
            final String hashedPassword = AccountFactory.this.passwordEncoderManager.hash( this.password );

            // Step 05: Create Account and return
            final AccountId id = AccountId.random();
            final Timestamp now = Timestamp.now();
            return Account.builder().id( id.value() ).owner( this.owner ).lang( this.lang ).role( role.value() )
                    .email( this.email ).password( hashedPassword ).createdAt( now.value() ).updatedAt( now.value() )
                    .build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
