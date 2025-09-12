package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.constraint.*;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag( "Entity" )
@Tag( "User" )
@Tag( "Factory" )
@DisplayName( "Tests for UserFactory" )
class UserFactoryTest {

//--------------------------------------------------------------------------------------------------------------------\\
//---| HELPER METHODS |-----------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    private UserIdConstraint mockUserIdConstraintOk(){
        final UserIdConstraint constraint = mock( UserIdConstraint.class );
        when( constraint.check( any( User.class ))).thenReturn( Optional.empty() );
        return constraint;
    }

    private UserEmailConstraint mockUserEmailConstraintOk(){
        final UserEmailConstraint constraint = mock( UserEmailConstraint.class );
        when( constraint.check( any( User.class ))).thenReturn( Optional.empty() );
        return constraint;
    }

    private UserUsernameConstraint mockUserUsernameConstraintOK(){
        final UserUsernameConstraint constraint = mock( UserUsernameConstraint.class );
        when( constraint.check( any( User.class ))).thenReturn( Optional.empty() );
        return constraint;
    }

    private UserBirthdateConstraint mockUserBirthdateConstraintOK(){
        final UserBirthdateConstraint constraint = mock( UserBirthdateConstraint.class );
        when( constraint.check( any( User.class ))).thenReturn( Optional.empty() );
        return constraint;
    }

    private UserImageConstraint mockUserImageConstraintOK() {
        final UserImageConstraint constraint = mock( UserImageConstraint.class );
        when( constraint.check( any( User.class ))).thenReturn( Optional.empty() );
        return constraint;
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "User creation succeeds when no constraint violations exist" )
    void givenValidUserDataAndNoConstraintViolations_whenBuildingUser_thenUserIsCreatedSuccessfully(){
        // Given mocked Constraints
        final UserIdConstraint userIdConstraint = this.mockUserIdConstraintOk();
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

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

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "User creation fails with ConstraintException when constraint violations exist" )
    void givenValidUserDataAndConstraintViolations_whenBuildingUser_thenThrowConstraintException(){
        // Given mocked ConstraintViolation that constraints will be return
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );

        // Given mocked UserIdConstraint that return violations
        final UserIdConstraint userIdConstraint = this.mockUserIdConstraintOk();
        when( userIdConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));

        // Given mocked UserEmailConstraint that return violations
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        when( userEmailConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));

        // Given mocked UserUsernameConstraint that return violations
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        when( userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));

        // Given mocked UserBirthdateConstraint that return violations
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        when( userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));

        // Given mocked UserImageConstraint that return violations
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();
        when( userImageConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));

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
        final UserFactory factory = User.create( email, username, role, name, birthdate, password ).image( image ).surname( surname );
        final ConstraintException exception = assertThrows( ConstraintException.class, () ->
                factory.build( userIdConstraint, userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint ));

        // Then check that constraints were checked
        verify( userIdConstraint ).check( any( User.class ));
        verify( userEmailConstraint ).check( any( User.class ));
        verify( userUsernameConstraint ).check( any( User.class ));
        verify( userBirthdateConstraint ).check( any( User.class ));
        verify( userImageConstraint ).check( any( User.class ));

        // Then assert thrown exception
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 5, violations.size() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
