package com.codenaiten.template.rest.app.factory;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.policy.UserUsernameUniquenessPolicy;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.user.UserId;
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
public class UserFactory {

    private final UserUsernameUniquenessPolicy userUsernameUniquenessPolicy;
    private final UserMinimumAgePolicy userMinimumAgePolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Factory create( final UserUsername username, final UserName name, final LocalDate birthdate ) {
        //Check Required fields
        if( Objects.isNull( username )) throw new ValidationException(AppMessage.ERROR_VALIDATION_USER_USERNAME_REQUIRED );
        if( Objects.isNull( name )) throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_NAME_REQUIRED );
        if( Objects.isNull( birthdate )) throw new ValidationException( AppMessage.ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED );

        return new Factory( username.value(), name.value(), birthdate );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @RequiredArgsConstructor
    public class Factory {
        private final String username;
        private final String name;
        private final LocalDate birthdate;
        private String surname;

        public Factory surname(final UserSurname surname ){
            this.surname = Optional.ofNullable( surname ).map( UserSurname::value ).orElse( null );
            return this;
        }

        public User build(){
            //Check if username is unique
            UserFactory.this.userUsernameUniquenessPolicy.check( new UserUsername( this.username ));

            //Check if user age is valid
            UserFactory.this.userMinimumAgePolicy.check( this.birthdate );

            //Create User
            final UserId id = UserId.random();
            final Timestamp now = Timestamp.now();
            return User.builder().id( id.value() ).username( this.username ).name( this.name ).surname( this.surname )
                    .birthdate( this.birthdate ).createdAt( now.value() ).updatedAt( now.value() ).build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
