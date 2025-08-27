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

@Slf4j
@RequiredArgsConstructor
public class UserEditor {

    private final UserUsernameUniquenessPolicy userUsernameUniquenessPolicy;
    private final UserMinimumAgePolicy userMinimumAgePolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Editor update( final User user ) {
        return new Editor( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EDITOR |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public class Editor {

        private final User user;
        private String username;
        private String name;
        private String surname;
        private LocalDate birthdate;

        public Editor( final User user ){
            this.user = user;
            this.username = user.getUsername();
            this.name = user.getName();
            this.surname = user.getSurname().orElse( null );
            this.birthdate = user.getBirthdate();
        }

        public Editor username( final UserUsername username ){
            //Check if username is null
            if( Objects.isNull( username ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_USERNAME_REQUIRED );

            //Check if username is unique
            UserEditor.this.userUsernameUniquenessPolicy.check( username );

            this.username = username.value();
            return this;
        }

        public Editor name( final UserName name ){
            //Check if name is null
            if( Objects.isNull( name ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_NAME_REQUIRED );
            this.name = name.value();
            return this;
        }

        public Editor surname( final UserSurname surname ){
            this.surname = Optional.ofNullable( surname ).map( UserSurname::value ).orElse( null );
            return this;
        }

        public Editor birthdate( final LocalDate birthdate ){
            //Check if birthdate is null
            if( Objects.isNull( birthdate ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED );

            //Check if user age is valid
            UserEditor.this.userMinimumAgePolicy.check( birthdate );

            this.birthdate = birthdate;
            return this;
        }

        public boolean hasChanges() {
            return !Objects.equals( this.username, this.user.getUsername() ) ||
                   !Objects.equals( this.name, this.user.getName() ) ||
                   !Objects.equals( this.surname, this.user.getSurname().orElse( null )) ||
                   !Objects.equals( this.birthdate, this.user.getBirthdate() );
        }

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
