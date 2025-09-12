package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.constraint.*;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag( "Entity" )
@Tag( "User" )
@Tag( "Factory" )
@DisplayName( "Tests for UserFactory" )
class UserFactoryTest {

    @Test
    @DisplayName( "Creation of a valid User when no constraint violations exist" )
    void givenValidUserDataAndNoConstraintViolations_whenBuildingUser_thenUserIsCreatedSuccessfully() {
        // Given mocked UserIdConstraint that return no violations
        final UserIdConstraint userIdConstraint = mock( UserIdConstraint.class );
        when( userIdConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given mocked UserEmailConstraint that return no violations
        final UserEmailConstraint userEmailConstraint = mock( UserEmailConstraint.class );
        when( userEmailConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given mocked UserUsernameConstraint that return no violations
        final UserUsernameConstraint userUsernameConstraint = mock( UserUsernameConstraint.class );
        when( userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given mocked UserBirthdateConstraint that return no violations
        final UserBirthdateConstraint userBirthdateConstraint = mock( UserBirthdateConstraint.class );
        when( userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given mocked UserImageConstraint that return no violations
        final UserImageConstraint userImageConstraint = mock( UserImageConstraint.class );
        when( userImageConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given valid User data
        final Email email = new Email("test@mail.cc" );
        final UserUsername username = new UserUsername( "test" );
        final UserRole role = UserRole.USER;
        final MediaId image = MediaId.random();
        final UserName name = new UserName( "Test" );
        final UserSurname surname = new UserSurname( "User" );
        final LocalDate birthdate = LocalDate.of( 1993, 2, 10 );
        final EncodedPassword password = new EncodedPassword( "encoded-password" );

        // When building the User
        final User user = User.create( email, username, role, name, birthdate, password ).image( image ).surname( surname )
                .build( userIdConstraint, userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then check that constraints were checked
        verify( userIdConstraint ).check( any( User.class ));
        verify( userEmailConstraint ).check( any( User.class ));
        verify( userUsernameConstraint ).check( any( User.class ));
        verify( userBirthdateConstraint ).check( any( User.class ));
        verify( userImageConstraint ).check( any( User.class ));

        // Then assert User was created successfully with correct data
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
