package com.codenaiten.template.rest.core.feature.user.util;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserBirthdatePolicy;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserEmailPolicy;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserImagePolicy;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserUsernamePolicy;
import com.codenaiten.template.rest.core.feature.user.spi.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.*;
import com.codenaiten.template.rest.core.shared.spi.PasswordEncoder;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Password;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidUserUsernamePolicy validUserUsernamePolicy;
    private final ValidUserEmailPolicy validUserEmailPolicy;
    private final ValidUserBirthdatePolicy validUserBirthdatePolicy;
    private final ValidUserImagePolicy validUserImagePolicy;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public User create(final Input input ){
        log.info( "Creating user: {}", input );

        // Get Default User Role
        UserRole defaultRole = UserRole.USER;
        if( this.userRepository.count() == 0 ) defaultRole = UserRole.ADMIN;

        // Get Data
        final UserId id = UserId.random();
        final UserUsername username = input.getUsername();
        final Email email = input.getEmail();
        final UserRole role = input.getRole().orElse( defaultRole );
        final MediaId image = input.getImage().orElse( null );
        final UserName name = input.getName();
        final UserSurname surname = input.getSurname().orElse( null );
        final LocalDate birthdate = input.getBirthdate();
        final EncodedPassword password = this.passwordEncoder.encode( input.getPassword() );
        final Timestamp createdAt = Timestamp.now();
        final Timestamp updatedAt = null;

        // Create User
        final User user = new User( id, email, username, role, image, name, surname, birthdate, password, createdAt, updatedAt );

        // Check Restrictions
        this.validUserUsernamePolicy.check( user );
        this.validUserEmailPolicy.check( user );
        this.validUserBirthdatePolicy.check( user );
        this.validUserImagePolicy.check( user );

        // Return User
        return user;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| INPUT |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Getter
    @Setter
    @RequiredArgsConstructor
    class Input {

        private final Email email;
        private final UserUsername username;
        private final UserName name;
        private final LocalDate birthdate;
        private final Password password;
        private UserRole role;
        private MediaId image;
        private UserSurname surname;

    //----------------------------------------------------------------------------------------------------------------\\
    //---| GETTERS |--------------------------------------------------------------------------------------------------\\
    //----------------------------------------------------------------------------------------------------------------\\

        public Optional<UserRole> getRole() {
            return Optional.ofNullable( this.role );
        }

        public Optional<MediaId> getImage() {
            return Optional.ofNullable( this.image );
        }

        public Optional<UserSurname> getSurname() {
            return Optional.ofNullable( this.surname );
        }

    //----------------------------------------------------------------------------------------------------------------\\

    }

//--------------------------------------------------------------------------------------------------------------------\\

}
