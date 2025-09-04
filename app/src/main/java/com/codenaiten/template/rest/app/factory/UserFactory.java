package com.codenaiten.template.rest.app.factory;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.policy.UserUsernameUniquenessPolicy;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Factory que permite crear objetos de tipo {@link User}.
 */
@Slf4j
@RequiredArgsConstructor
public class UserFactory {

    /** Policy que comprueba si un {@link UserUsername} de un {@link User} es único */
    private final UserUsernameUniquenessPolicy userUsernameUniquenessPolicy;

    /** Policy que comprueba si la edad de un {@link User} es igual o superior a la permitida */
    private final UserMinimumAgePolicy userMinimumAgePolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite crear un objeto de tipo {@link User} a partir de los datos obligatorios proporcionados.
     *
     * @param username {@link UserUsername} que representa la información del username de usuario del {@link User}.
     * @param name {@link UserName} que representa la informaución del nombre del {@link User}.
     * @param birthdate {@link LocalDate} que representa la fecha de nacimiento del {@link User}.
     *
     * @return {@link Factory} con los datos proporcionados del {@link User}.
     */
    public Factory create( final UserUsername username, final UserName name, final LocalDate birthdate ) {
        // Step 01: Check Required fields
        if( Objects.isNull( username )) throw new ValidationException(AppMessage.ERROR_VALIDATION_USER_USERNAME_REQUIRED );
        if( Objects.isNull( name )) throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_NAME_REQUIRED );
        if( Objects.isNull( birthdate )) throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED );

        // Step 02 - Return Factory
        return new Factory( username.value(), name.value(), birthdate );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clase que permite crear un objeto de tipo {@link User} con un patrón Builder.
     */
    @RequiredArgsConstructor
    public class Factory {

        /** Información requerida del {@link UserUsername} del {@link User} */
        private final String username;

        /** Información requerida del {@link UserName} del {@link User} */
        private final String name;

        /** Información requerida de la fecha de nacimiento del {@link User} */
        private final LocalDate birthdate;

    // -------------------------------------------------------------------------------------------------------------- \\

        /** Información opcional del {@link ImageId} de la {@link Image} que representa la image de perfil del {@link User} */
        private UUID image;

        /** Información opcional del {@link UserSurname} del {@link User} */
        private String surname;

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite asignar el {@link ImageId} de la {@link Image} que representa la image de perfil del {@link User}.
         *
         * @param image {@link ImageId} que representa el id de la imagen de perfil del {@link User}.
         *
         * @return {@link Factory} con el {@link ImageId} asignado.
         */
        public Factory image( final ImageId image ){
            this.image = Optional.ofNullable( image ).map( ImageId::value ).orElse( null );
            return this;
        }

        /**
         * Permite asignar el {@link UserSurname} del {@link User}.
         *
         * @param surname {@link UserSurname} que representa el apellido del {@link User}.
         *
         * @return {@link Factory} con el {@link UserSurname} asignado.
         */
        public Factory surname( final UserSurname surname ){
            this.surname = Optional.ofNullable( surname ).map( UserSurname::value ).orElse( null );
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite crear un objeto de tipo {@link User} a partir de los datos proporcionados en el {@link Factory}.
         *
         * @return {@link User} creado a partir de los datos proporcionados en el {@link Factory}.
         */
        public User build(){
            // Step 01: Check if username is unique
            UserFactory.this.userUsernameUniquenessPolicy.check( new UserUsername( this.username ));

            // Step 02: Check if user age is valid
            UserFactory.this.userMinimumAgePolicy.check( this.birthdate );

            // Step 03: Create User and return
            final UserId id = UserId.random();
            final Timestamp now = Timestamp.now();
            return User.builder().id( id.value() ).image( this.image ).username( this.username ).name( this.name )
                    .surname( this.surname ).birthdate( this.birthdate ).createdAt( now.value() ).updatedAt( now.value() )
                    .build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
