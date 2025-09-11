package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.constraint.*;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserFactoryTest {

    @Test
    void name() {
        final UserIdConstraint userIdConstraint = mock( UserIdConstraint.class );
        when( userIdConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        final UserEmailConstraint userEmailConstraint = mock( UserEmailConstraint.class );
        when( userEmailConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        final UserUsernameConstraint userUsernameConstraint = mock( UserUsernameConstraint.class );
        when( userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        final UserBirthdateConstraint userBirthdateConstraint = mock( UserBirthdateConstraint.class );
        when( userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        final UserImageConstraint userImageConstraint = mock( UserImageConstraint.class );
        when( userImageConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        final Email email = new Email("test@mail.cc" );
        final UserUsername username = new UserUsername( "test" );
        final UserRole role = UserRole.USER;
        final MediaId image = MediaId.random();
        final UserName name = new UserName( "Test" );
        final UserSurname surname = new UserSurname( "User" );
        final LocalDate birthdate = LocalDate.of( 1993, 2, 10 );
        final EncodedPassword password = new EncodedPassword( "encoded-password" );

        final User user = User.create( email, username, role, name, birthdate, password ).image( image ).surname( surname )
                .build( userIdConstraint, userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        verify( userIdConstraint ).check( any( User.class ));
        verify( userEmailConstraint ).check( any( User.class ));
        verify( userUsernameConstraint ).check( any( User.class ));
        verify( userBirthdateConstraint ).check( any( User.class ));
        verify( userImageConstraint ).check( any( User.class ));

        assertNotNull( user.getId() );
        assertEquals( email, user.getEmail() );
        assertEquals( username, user.getUsername() );
        assertEquals( role, user.getRole() );
        assertEquals( image, user.getImage().orElse( null ) );
        assertEquals( name, user.getName() );
        assertEquals( surname, user.getSurname().orElse( null ) );
        assertEquals( birthdate, user.getBirthdate() );
        assertEquals( password, user.getPassword() );
        assertNotNull( user.getCreatedAt() );
        assertTrue( user.getUpdatedAt().isEmpty() );
    }
}
