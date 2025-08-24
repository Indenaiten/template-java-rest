package com.codenaiten.template.rest.app.factory;

import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Builder create( final UserRole role, final UserUsername username, final UserName name, final LocalDate birthdate ) {
        //Check Required fields
        if( Objects.isNull( role ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_ROLE_REQUIRED );
        if( Objects.isNull( username )) throw new ValidationException(AppMessage.ERROR_VALIDATION_USER_USERNAME_REQUIRED );
        if( Objects.isNull( name )) throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_NAME_REQUIRED );
        if( Objects.isNull( birthdate )) throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED );

        return new Builder( role.value(), username.value(), name.value(), birthdate );
    }

    public Builder create( final UserUsername username, final UserName name, final LocalDate birthdate ) {
        final UserRole role = UserRole.USER;
        return create( role, username, name, birthdate );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @RequiredArgsConstructor
    public class Builder {
        private final Integer role;
        private final String username;
        private final String name;
        private final LocalDate birthdate;
        private String surname;

        public Builder surname( final UserSurname surname ){
            this.surname = Optional.ofNullable( surname ).map( UserSurname::value ).orElse( null );
            return this;
        }

        public User build(){
            //Check if username is unique
            if( UserFactory.this.userRepository.existsByUsername( this.username ))
                throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_USERNAME_ALREADY_EXISTS, this.username);

            //Check if user age is valid
            final UserMinimumAgePolicy userMinimumAgePolicy = new UserMinimumAgePolicy();
            userMinimumAgePolicy.check( this.birthdate );

            //If is the first user, set role to ADMIN
            Integer role = this.role;
            if( UserFactory.this.userRepository.count() == 0 ) role = UserRole.ADMIN.value();

            //Create User
            final UserId id = UserId.random();
            final Timestamp now = Timestamp.now();
            return User.builder().id( id.value() ).role( role ).username( this.username ).name( this.name )
                    .surname( this.surname ).birthdate( this.birthdate ).createdAt( now.value() )
                    .updatedAt( now.value() ).build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
