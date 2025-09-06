package com.codenaiten.template.rest.app.old.editor;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.old.policy.AccountEmailUniquenessPolicy;
import com.codenaiten.template.rest.app.old.policy.AssignAccountRolePolicy;
import com.codenaiten.template.rest.app.old.policy.LanguageSupportedPolicy;
import com.codenaiten.template.rest.app.old.vo.Email;
import com.codenaiten.template.rest.app.old.vo.Timestamp;
import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Editor que permite actualizar objetos de tipo {@link Account}.
 */
@Slf4j
@RequiredArgsConstructor
public class AccountEditor {

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
     * Permite actualizar la información de un objeto de tipo {@link Account}.
     *
     * @param account {@link Account} que representa la información de la cuenta de usuario que se va a actualizar.
     *
     * @return {@link Editor} con la {@link Account} proporcionado para ser actualizado.
     */
    public Editor update( final Account account ) {
        return new Editor( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EDITOR |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clase que permite actualizar un objeto de tipo {@link Account} con un patrón Builder.
     */
    public class Editor {

        /** Instancia del {@link Account} que se va a actualizar */
        private final Account account;

    // -------------------------------------------------------------------------------------------------------------- \\

        /** Información del {@link Locale} que se va a actualizar en la instancia de la {@link Account} */
        private String lang;

        /** Información del {@link AccountRole} que se va a actualizar en la instancia de la {@link Account} */
        private Integer role;

        /** Información del {@link Email} que se va a actualizar en la instancia de la {@link Account} */
        private String email;

        /** Información del {@link AccountPassword} que se va a actualizar en la instancia de la {@link Account} */
        private String password;

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Constructor que recibe un objeto de tipo {@link Account} para ser actualizado y establece a partir de este los
         * valores actuales de cuenta de usuario.
         *
         * @param account {@link Account} que representa la cuenta de usuario que se va a actualizar.
         */
        public Editor( final Account account ){
            this.account = account;
            this.role = account.getRole();
            this.email = account.getEmail();
            this.password = account.getPassword();
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite asignar un nuevo {@link Locale} en la {@link Account} proporcionado en el {@link Editor}.
         *
         * @param lang {@link Locale} que representa el nuevo lenguaje de la {@link Account}.
         *
         * @return {@link Editor} con el nuevo {@link Locale} asignado.
         */
        public Editor lang( Locale lang ){
            // Step 01: Set new lang if exists, otherwise set actual locale
            lang = Optional.ofNullable( lang ).orElse( LocaleContextHolder.getLocale() );

            // Step 02: Check if lang is supported
            AccountEditor.this.languageSupportedPolicy.check( lang );

            // Step 03: Set new lang
            this.lang = lang.toLanguageTag();
            return this;
        }

        /**
         * Permite asignar un nuevo {@link AccountRole} en la {@link Account} proporcionada en el {@link Editor}.
         *
         * @param role {@link AccountRole} que representa el nuevo role de la {@link Account}.
         *
         * @return {@link Editor} con el nuevo {@link AccountRole} asignado.
         */
        public Editor role( final AccountRole role ){
            this.role = AccountEditor.this.assignAccountRolePolicy.get( role ).value();
            return this;
        }

        /**
         * Permite asignar un nuevo {@link Email} en la {@link Account} proporcionada en el {@link Editor}.
         *
         * @param email {@link Email} que representa el nuevo email de la {@link Account}.
         *
         * @return {@link Editor} con el nuevo {@link Email} asignado.
         */
        public Editor email( final Email email ){
            // Step 01: Check if email is null
            if( Objects.isNull( email ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_EMAIL_REQUIRED );

            // Step 02: Check if username is unique
            if( !Objects.equals( email.value(), this.email )) AccountEditor.this.accountEmailUniquenessPolicy.check( email );

            // Step 03: Set new email
            this.email = email.value();
            return this;
        }

        /**
         * Permite asignar un nuevo {@link AccountPassword} en la {@link Account} proporcionada en el {@link Editor}.
         *
         * @param password {@link AccountPassword} que representa el nuevo password de la {@link Account}.
         *
         * @return {@link Editor} con el nuevo {@link AccountPassword} asignado.
         */
        public Editor password( final AccountPassword password ){
            // Step 01: Check if password is null
            if( Objects.isNull( password ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_ACCOUNT_PASSWORD_REQUIRED );

            // Step 02: Encode password
            final String hashedPassword = AccountEditor.this.passwordEncoderManager.hash( password.value() );

            // Step 03: Set new password
            this.password = hashedPassword;
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite determinar si hay datos en el {@link Editor} diferentes de los que ya tiene la {@link Account}
         * proporcionada.
         *
         * @return {@code true} si hay cambios, {@code false} en caso contrario.
         */
        public boolean hasChanges() {
            return !Objects.equals( this.lang, this.account.getLang().orElse( null )) ||
                   !Objects.equals( this.role, this.account.getRole() ) ||
                   !Objects.equals( this.email, this.account.getEmail() ) ||
                   !Objects.equals( this.password, this.account.getPassword() );
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite aplicar los cambios proporcionados en el {@link Editor} a la instancia de la {@link Account}.
         */
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
