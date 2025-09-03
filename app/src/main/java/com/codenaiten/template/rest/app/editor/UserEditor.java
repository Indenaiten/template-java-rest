package com.codenaiten.template.rest.app.editor;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.policy.UserUsernameUniquenessPolicy;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * Editor que permite actualizar objetos de tipo {@link User}.
 */
@Slf4j
@RequiredArgsConstructor
public class UserEditor {

    /** Policy que comprueba si un {@link UserUsername} de un {@link User} es único */
    private final UserUsernameUniquenessPolicy userUsernameUniquenessPolicy;

    /** Policy que comprueba si la edad de un {@link User} es igual o superior a la permitida */
    private final UserMinimumAgePolicy userMinimumAgePolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite actualizar la información de un objeto de tipo {@link User}.
     *
     * @param user {@link User} que representa la información del usuario que se va a actualizar.
     *
     * @return {@link Editor} con el {@link User} proporcionado para ser actualizado.
     */
    public Editor update( final User user ) {
        return new Editor( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EDITOR |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clase que permite actualizar un objeto de tipo {@link User} con un patrón Builder.
     */
    public class Editor {

        /** Instancia del {@link User} que se va a actualizar */
        private final User user;

    // -------------------------------------------------------------------------------------------------------------- \\

        /** Información del {@link UserUsername} que se va a actualizar en la instancia del {@link User} */
        private String username;

        /** Información del {@link UserName} que se va a actualizar en la instancia del {@link User} */
        private String name;

        /** Información del {@link UserSurname} que se va a actualizar en la instancia del {@link User} */
        private String surname;

        /** Información de la fecha de nacimiento que se va a actualizar en la instancia del {@link User} */
        private LocalDate birthdate;

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Constructor que recibe un objeto de tipo {@link User} para ser actualizado y establece a partir de este los
         * valores actuales del usuario.
         *
         * @param user {@link User} que representa el usuario que se va a actualizar.
         */
        public Editor( final User user ){
            this.user = user;
            this.username = user.getUsername();
            this.name = user.getName();
            this.surname = user.getSurname().orElse( null );
            this.birthdate = user.getBirthdate();
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite asignar un nuevo {@link UserUsername} en el {@link User} proporcionado en el {@link Editor}.
         *
         * @param username {@link UserUsername} que representa el nuevo username del {@link User}.
         *
         * @return {@link Editor} con el nuevo {@link UserUsername} asignado.
         */
        public Editor username( final UserUsername username ){
            // Step 01: Check if username is null
            if( Objects.isNull( username ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_USERNAME_REQUIRED );

            // Step 02: Check if username is unique
            UserEditor.this.userUsernameUniquenessPolicy.check( username );

            // Step 03: Set new username
            this.username = username.value();
            return this;
        }

        /**
         * Permite asignar un nuevo {@link UserName} en el {@link User} proporcionado en el {@link Editor}.
         *
         * @param name {@link UserName} que representa el nuevo nombre del {@link User}.
         *
         * @return {@link Editor} con el nuevo {@link UserName} asignado.
         */
        public Editor name( final UserName name ){
            // Step 01: Check if name is null
            if( Objects.isNull( name ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_NAME_REQUIRED );

            // Step 02: Set new name
            this.name = name.value();
            return this;
        }

        /**
         * Permite asignar un nuevo {@link UserSurname} en el {@link User} proporcionado en el {@link Editor}.
         *
         * @param surname {@link UserSurname} que representa el nuevo apellido del {@link User}.
         *
         * @return {@link Editor} con el nuevo {@link UserSurname} asignado.
         */
        public Editor surname( final UserSurname surname ){
            this.surname = Optional.ofNullable( surname ).map( UserSurname::value ).orElse( null );
            return this;
        }

        /**
         * Permite asignar una nueva fecha de nacimiento en el {@link User} proporcionado en el {@link Editor}.
         *
         * @param birthdate {@link LocalDate} que representa la nueva fecha de nacimiento del {@link User}.
         *
         * @return {@link Editor} con la nueva fecha de nacimiento asignada.
         */
        public Editor birthdate( final LocalDate birthdate ){
            // Step 01: Check if birthdate is null
            if( Objects.isNull( birthdate ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED );

            // Step 02: Check if user age is valid
            UserEditor.this.userMinimumAgePolicy.check( birthdate );

            // Step 03: Set new birthdate
            this.birthdate = birthdate;
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite determinar si hay datos en el {@link Editor} diferentes de los que ya tiene el {@link User}
         * proporcionado.
         *
         * @return {@code true} si hay cambios, {@code false} en caso contrario.
         */
        public boolean hasChanges() {
            return !Objects.equals( this.username, this.user.getUsername() ) ||
                   !Objects.equals( this.name, this.user.getName() ) ||
                   !Objects.equals( this.surname, this.user.getSurname().orElse( null )) ||
                   !Objects.equals( this.birthdate, this.user.getBirthdate() );
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite aplicar los cambios proporcionados en el {@link Editor} a la instancia del {@link User}.
         */
        public void apply(){
            if( this.hasChanges() ){
                this.user.setUsername( this.username );
                this.user.setName( this.name );
                this.user.setSurname( this.surname );
                this.user.setBirthdate( this.birthdate );
                this.user.setUpdatedAt( Timestamp.now().value() );
            }
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
