package com.codenaiten.template.rest.core.layer.service.impl;

import com.codenaiten.template.rest.core.layer.entity.User;
import com.codenaiten.template.rest.core.layer.exception.AppException;
import com.codenaiten.template.rest.core.layer.service.UpdateUserService;
import com.codenaiten.template.rest.core.layer.spec.UniquenessUserEmailSpec;
import com.codenaiten.template.rest.core.layer.spec.UniquenessUserUsernameSpec;
import com.codenaiten.template.rest.core.layer.spec.UserMinimumAgeSpec;
import com.codenaiten.template.rest.core.layer.spi.PasswordEncoder;
import com.codenaiten.template.rest.core.layer.vo.Email;
import com.codenaiten.template.rest.core.layer.vo.EncodedPassword;
import com.codenaiten.template.rest.core.layer.vo.Password;
import com.codenaiten.template.rest.core.layer.vo.media.MediaId;
import com.codenaiten.template.rest.core.layer.vo.user.UserName;
import com.codenaiten.template.rest.core.layer.vo.user.UserRole;
import com.codenaiten.template.rest.core.layer.vo.user.UserSurname;
import com.codenaiten.template.rest.core.layer.vo.user.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUserServiceImpl implements UpdateUserService {

    private final PasswordEncoder passwordEncoder;
    private final UniquenessUserUsernameSpec uniquenessUserUsernameSpec;
    private final UniquenessUserEmailSpec uniquenessUserEmailSpec;
    private final UserMinimumAgeSpec userMinimumAgeSpec;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public void updateEmail( final User user, final Email email ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update email is required" );
        if( !Objects.equals( user.getEmail(), email )){
            user.setEmail( email );
            if( this.uniquenessUserEmailSpec.not().test( user )) throw new AppException();
        }
    }

    @Override
    public void updateUsername( final User user, final UserUsername username ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update username is required" );
        if( !Objects.equals( user.getUsername(), username )){
            user.setUsername( username );
            if( this.uniquenessUserUsernameSpec.not().test( user )) throw new AppException();
        }
    }

    @Override
    public void updateRole( final User user, final UserRole role ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update role is required" );
        if( !Objects.equals( user.getRole(), role )) user.setRole( role );
    }

    @Override
    public void updateImage( final User user, final MediaId image ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update image is required" );
        if( !Objects.equals( user.getImage().orElse( null ), image )) user.setImage( image );
    }

    @Override
    public void updateName( final User user, final UserName name ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update name is required" );
        if( !Objects.equals( user.getName(), name )) user.setName( name );
    }

    @Override
    public void updateSurname( final User user, final UserSurname surname ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update surname is required" );
        if( !Objects.equals( user.getSurname().orElse( null ), surname )) user.setSurname( surname );
    }

    @Override
    public void updateBirthdate( final User user, final LocalDate birthdate ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update birthdate is required" );
        if( !Objects.equals( user.getBirthdate(), birthdate )){
            user.setBirthdate( birthdate );
            if( this.userMinimumAgeSpec.not().test( user )) throw new AppException();
        }
    }

    @Override
    public void updatePassword( final User user, final Password password ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update password is required" );
        if( this.passwordEncoder.notMatches( password, user.getPassword() )){
            final EncodedPassword encodedPassword = this.passwordEncoder.encode( password );
            user.setPassword( encodedPassword );
        }
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
