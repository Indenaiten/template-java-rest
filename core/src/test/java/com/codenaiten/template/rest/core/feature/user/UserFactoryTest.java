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
import org.junit.jupiter.api.BeforeEach;
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

    private UserIdConstraint userIdConstraint;
    private UserEmailConstraint userEmailConstraint;
    private UserUsernameConstraint userUsernameConstraint;
    private UserBirthdateConstraint userBirthdateConstraint;
    private UserImageConstraint userImageConstraint;

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETUP |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @BeforeEach
    void setUp(){
        this.userIdConstraint = mock( UserIdConstraint.class );
        this.userEmailConstraint = mock( UserEmailConstraint.class );
        this.userUsernameConstraint = mock( UserUsernameConstraint.class );
        this.userBirthdateConstraint = mock( UserBirthdateConstraint.class );
        this.userImageConstraint = mock( UserImageConstraint.class );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| TESTS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link UserFactory} construye un {@link User} con datos válidos que no violan las
     * restricciones requeridas por la factoría para construir el {@link User}.
     */
    @Test
    @DisplayName( "Given valid user data that does not violate constraints, When build is called, Then the user is created successfully" )
    void givenValidUserDataThatNotViolationConstraints_whenBuildCalled_thenUserCreatedSuccessfully(){
        // Mocks Setting
        when( this.userIdConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userEmailConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userImageConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given valid User data
        final Email email = new Email("test@mail.cc" );
        final UserUsername username = new UserUsername( "test" );
        final UserRole role = UserRole.USER;
        final MediaId image = MediaId.random();
        final UserName name = new UserName( "Name Test" );
        final UserSurname surname = new UserSurname( "Surname Test" );
        final LocalDate birthdate = LocalDate.of( 1993, 2, 10 );
        final EncodedPassword password = new EncodedPassword( "encoded-password" );

        // When building the User
        final User user = User.create( email, username, role, name, birthdate, password )
                .image( image )
                .surname( surname )
                .build( this.userIdConstraint,
                        this.userEmailConstraint,
                        this.userUsernameConstraint,
                        this.userBirthdateConstraint,
                        this.userImageConstraint );

        // Then check that constraints were checked
        verify( this.userIdConstraint ).check( any( User.class ));
        verify( this.userEmailConstraint ).check( any( User.class ));
        verify( this.userUsernameConstraint ).check( any( User.class ));
        verify( this.userBirthdateConstraint ).check( any( User.class ));
        verify( this.userImageConstraint ).check( any( User.class ));

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

    /**
     * Test que comprueba que la clase {@link UserFactory} lanza la excepción {@link ConstraintException} cuando se
     * intenta construir un {@link User} con datos válidos que violan las restricciones requeridas por la factoría para
     * construir el {@link User}.
     */
    @Test
    @DisplayName( "Given user data that violates constraints, When build is called, Then a ConstraintException is thrown" )
    void givenValidUserDataThatViolationConstraints_whenBuildCalled_thenThrowConstraintException(){
        // Mocks Setting
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );
        when( this.userIdConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));
        when( this.userEmailConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));
        when( this.userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));
        when( this.userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));
        when( this.userImageConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));

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
        final ConstraintException exception = assertThrows( ConstraintException.class,
                () -> factory.build( this.userIdConstraint,
                                     this.userEmailConstraint,
                                     this.userUsernameConstraint,
                                     this.userBirthdateConstraint,
                                     this.userImageConstraint ));

        // Then check that constraints were checked
        verify( this.userIdConstraint ).check( any( User.class ));
        verify( this.userEmailConstraint ).check( any( User.class ));
        verify( this.userUsernameConstraint ).check( any( User.class ));
        verify( this.userBirthdateConstraint ).check( any( User.class ));
        verify( this.userImageConstraint ).check( any( User.class ));

        // Then assert thrown exception
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 5, violations.size() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
