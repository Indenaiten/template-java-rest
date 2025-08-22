package com.codenaiten.template.rest.app.editor;

import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserRole;
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

    private final UserRepository userRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Editor update( final User user ) {
        return new Editor( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EDITOR |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @RequiredArgsConstructor
    public class Editor {

        private final User user;
        private Integer role;
        private String username;
        private String name;
        private String surname;
        private LocalDate birthdate;

        public Editor role( final UserRole role ){
            this.role = Optional.ofNullable( role ).map( UserRole::value ).orElse( UserRole.USER.value() );
            return this;
        }

        public Editor username( final UserUsername username ){
            //Check if username is null
            if( Objects.isNull( username )) throw new IllegalArgumentException( "Username cannot be null" );
            final String value = username.value();

            //Check if username is unique
            if( UserEditor.this.userRepository.existsByUsername( value ))
                throw new IllegalArgumentException( "Username already exists: %s".formatted( value ));

            this.username = value;
            return this;
        }

        public Editor name( final UserName name ){
            //Check if name is null
            if( Objects.isNull( name )) throw new IllegalArgumentException( "Name cannot be null" );
            this.name = name.value();
            return this;
        }

        public Editor surname( final UserSurname surname ){
            this.surname = Optional.ofNullable( surname ).map( UserSurname::value ).orElse( null );
            return this;
        }

        public Editor birthdate( final LocalDate birthdate ){
            //Check if birthdate is null
            if( Objects.isNull( birthdate )) throw new IllegalArgumentException( "Birthdate cannot be null" );

            //Check if user age is valid
            final UserMinimumAgePolicy userMinimumAgePolicy = new UserMinimumAgePolicy();
            userMinimumAgePolicy.check( this.birthdate );

            this.birthdate = birthdate;
            return this;
        }

        public boolean hasChanges() {
            return !Objects.equals( this.role, this.user.getRole() ) ||
                   !Objects.equals( this.username, this.user.getUsername() ) ||
                   !Objects.equals( this.name, this.user.getName() ) ||
                   !Objects.equals( this.surname, this.user.getSurname().orElse( null )) ||
                   !Objects.equals( this.birthdate, this.user.getBirthdate() );
        }

        public void apply(){
            if( this.hasChanges() ){
                this.user.setRole( this.role );
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
