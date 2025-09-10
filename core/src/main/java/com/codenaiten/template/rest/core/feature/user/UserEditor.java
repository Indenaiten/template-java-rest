package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.feature.media.spi.MediaRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserBirthdatePolicy;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserEmailPolicy;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserImagePolicy;
import com.codenaiten.template.rest.core.feature.user.policy.ValidUserUsernamePolicy;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.spi.PasswordEncoder;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEditor {

    private final MediaRepository mediaRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidUserUsernamePolicy validUserUsernamePolicy;
    private final ValidUserEmailPolicy validUserEmailPolicy;
    private final ValidUserBirthdatePolicy validUserBirthdatePolicy;
    private final ValidUserImagePolicy validUserImagePolicy;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public void updateEmail(final User user, final Email email ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update email is required" );
        if( !Objects.equals( user.getEmail(), email )){
            user.setEmail( email );
            this.validUserEmailPolicy.check( user );
        }
    }

    public void updateUsername( final User user, final UserUsername username ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update username is required" );
        if( !Objects.equals( user.getUsername(), username )){
            user.setUsername( username );
            this.validUserUsernamePolicy.check( user );
        }
    }

    public void updateRole( final User user, final UserRole role ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update role is required" );
        if( !Objects.equals( user.getRole(), role )) user.setRole( role );
    }

    public void updateImage( final User user, final MediaId image ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update image is required" );
        final MediaId currentImage = user.getImage().orElse( null );
        if( !Objects.equals( currentImage, image )){
            user.setImage( image );
            this.validUserImagePolicy.check( user );
        }
    }

    public void updateName( final User user, final UserName name ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update name is required" );
        if( !Objects.equals( user.getName(), name )) user.setName( name );
    }

    public void updateSurname( final User user, final UserSurname surname ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update surname is required" );
        if( !Objects.equals( user.getSurname().orElse( null ), surname )) user.setSurname( surname );
    }

    public void updateBirthdate( final User user, final LocalDate birthdate ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update birthdate is required" );
        if( !Objects.equals( user.getBirthdate(), birthdate )){
            user.setBirthdate( birthdate );
            this.validUserBirthdatePolicy.check( user );
        }
    }

    public void updatePassword( final User user, final Password password ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to update password is required" );
        if( this.passwordEncoder.notMatches( password, user.getPassword() )){
            final EncodedPassword encodedPassword = this.passwordEncoder.encode( password );
            user.setPassword( encodedPassword );
        }
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
