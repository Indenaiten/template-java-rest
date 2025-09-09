package com.codenaiten.template.rest.core.layer.service.impl;

import com.codenaiten.template.rest.core.layer.entity.User;
import com.codenaiten.template.rest.core.layer.exception.AppException;
import com.codenaiten.template.rest.core.layer.policy.DefaultUserRolePolicy;
import com.codenaiten.template.rest.core.layer.service.CreateUserService;
import com.codenaiten.template.rest.core.layer.spec.UniquenessUserEmailSpec;
import com.codenaiten.template.rest.core.layer.spec.UniquenessUserUsernameSpec;
import com.codenaiten.template.rest.core.layer.spec.UserMinimumAgeSpec;
import com.codenaiten.template.rest.core.layer.spi.PasswordEncoder;
import com.codenaiten.template.rest.core.layer.vo.Email;
import com.codenaiten.template.rest.core.layer.vo.EncodedPassword;
import com.codenaiten.template.rest.core.layer.vo.Timestamp;
import com.codenaiten.template.rest.core.layer.vo.media.MediaId;
import com.codenaiten.template.rest.core.layer.vo.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserServiceImpl implements CreateUserService {

    private final PasswordEncoder passwordEncoder;
    private final DefaultUserRolePolicy defaultUserRolePolicy;
    private final UniquenessUserUsernameSpec uniquenessUserUsernameSpec;
    private final UniquenessUserEmailSpec uniquenessUserEmailSpec;
    private final UserMinimumAgeSpec userMinimumAgeSpec;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public User create( final Input input ){
        log.info( "Creating user: {}", input );

        // Get Data
        final UserId id = UserId.random();
        final UserUsername username = input.getUsername();
        final Email email = input.getEmail();
        final UserRole role = input.getRole().orElse( this.defaultUserRolePolicy.detDefault() );
        final MediaId image = input.getImage().orElse( null );
        final UserName name = input.getName();
        final UserSurname surname = input.getSurname().orElse( null );
        final LocalDate birthdate = input.getBirthdate();
        final EncodedPassword password = this.passwordEncoder.encode( input.getPassword() );
        final Timestamp createdAt = Timestamp.now();
        final Timestamp updatedAt = null;

        // Create User
        final User user = new User( id, email, username, role, image, name, surname, birthdate, password, createdAt, updatedAt );

        // Check
        if( this.uniquenessUserUsernameSpec.not().test( user )) throw new AppException();
        if( this.uniquenessUserEmailSpec.not().test( user )) throw new AppException();
        if( this.userMinimumAgeSpec.not().test( user )) throw new AppException();

        // Return User
        return user;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
