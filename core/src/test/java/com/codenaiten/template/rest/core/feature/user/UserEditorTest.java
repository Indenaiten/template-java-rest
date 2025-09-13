package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.constraint.UserBirthdateConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserEmailConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserImageConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserUsernameConstraint;
import com.codenaiten.template.rest.core.feature.user.vo.*;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
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
@Tag( "Editor" )
@DisplayName( "Tests for UserEditor" )
class UserEditorTest {

    private UserEmailConstraint userEmailConstraint;
    private UserUsernameConstraint userUsernameConstraint;
    private UserBirthdateConstraint userBirthdateConstraint;
    private UserImageConstraint userImageConstraint;

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETUP |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @BeforeEach
    void setUp(){
        this.userEmailConstraint = mock( UserEmailConstraint.class );
        this.userUsernameConstraint = mock( UserUsernameConstraint.class );
        this.userBirthdateConstraint = mock( UserBirthdateConstraint.class );
        this.userImageConstraint = mock( UserImageConstraint.class );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| HELPER METHODS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Crea un nuevo {@link User} para utilizarlo en los tests.
     *
     * @return {@link User} para utilizarlo en los tests.
     */
    public static User createNewUser(){
        final UserId id = UserId.random();
        final Email email = new Email( "test@mail.cc" );
        final UserUsername username = new UserUsername( "test" );
        final UserRole role = UserRole.USER;
        final MediaId image = MediaId.of( "7575299c-c194-4af6-82b1-5fb29664f724" );
        final UserName name = new UserName( "Name Test" );
        final UserSurname surname = new UserSurname( "Surname Test" );
        final LocalDate birthdate = LocalDate.of( 1993, 2, 10 );
        final EncodedPassword password = new EncodedPassword( "encoded-password" );
        final Timestamp createdAt = Timestamp.now();

        return new User( id, email, username, role, image, name, surname, birthdate, password, createdAt, null );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| TESTS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link UserEditor} actualiza un {@link User} con nuevos datos, el editor devuelve
     * {@code true}, actualiza los correspondientes campos incluyendo el campo {@code updatedAt} con el {@link Timestamp}
     * de la fecha y hora de actualización en la instacia {@link User} y no lanza la excepción {@link ConstraintException}.
     */
    @Test
    @DisplayName( "Given valid user data that does not violate constraints, When apply is called, Then it returns true, the user is updated, and no constraints are triggered" )
    void givenValidUserDataThatNotViolationConstraints_whenApplyCalled_thenReturnTrueAndUserUpdatedAndConstraintsNotTriggered(){
        // Mocks Setting
        when( this.userEmailConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userImageConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given Valid data
        final Email email = new Email("new@mail.cc" );
        final UserUsername username = new UserUsername( "new" );
        final UserRole role = UserRole.ADMIN;
        final MediaId image = MediaId.random();
        final UserName name = new UserName( "Name New" );
        final UserSurname surname = new UserSurname( "Surname New" );
        final LocalDate birthdate = LocalDate.of( 2000, 1, 5 );
        final EncodedPassword password = new EncodedPassword( "new-encoded-password" );

        // Given Valid User
        final User user = createNewUser();

        // Update User Data
        final UserEditor editor = user.update().email( email ).username( username ).role( role ).image( image )
                .name( name ).surname( surname ).birthdate( birthdate ).password( password );

        // Check User Data
        assertNotEquals( email, user.getEmail() );
        assertNotEquals( username, user.getUsername() );
        assertNotEquals( role, user.getRole() );
        assertNotEquals( image, user.getImage().orElse( null ) );
        assertNotEquals( name, user.getName() );
        assertNotEquals( surname, user.getSurname().orElse( null ) );
        assertNotEquals( birthdate, user.getBirthdate() );
        assertNotEquals( password, user.getPassword() );
        assertTrue( user.getUpdatedAt().isEmpty(), "UpdatedAt should be empty" );

        // When editor apply changes
        final boolean changed = assertDoesNotThrow( () -> editor.apply( this.userEmailConstraint,
                                                                        this.userUsernameConstraint,
                                                                        this.userBirthdateConstraint,
                                                                        this.userImageConstraint ));

        // Then check that constraints were checked
        verify( this.userEmailConstraint ).check( any( User.class ) );
        verify( this.userUsernameConstraint ).check( any( User.class ) );
        verify( this.userBirthdateConstraint ).check( any( User.class ) );
        verify( this.userBirthdateConstraint ).check( any( User.class ) );

        // Then assert that return true and changes were made
        assertTrue( changed, "Should have changed" );
        assertEquals( email, user.getEmail() );
        assertEquals( username, user.getUsername() );
        assertEquals( role, user.getRole() );
        assertEquals( image, user.getImage().orElse( null ) );
        assertEquals( name, user.getName() );
        assertEquals( surname, user.getSurname().orElse( null ) );
        assertEquals( birthdate, user.getBirthdate() );
        assertEquals( password, user.getPassword() );
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should not be empty" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link UserEditor} no actualiza un {@link User} si se proporcionan datos que violan
     * las restricciones requeridas por el {@link UserEditor} y lanza la excepción {@link ConstraintException} con una
     * lista de {@link ConstraintViolation} que contiene las violaciones que se han producido.
     */
    @Test
    @DisplayName( "Given user data that violates constraints, When apply is called, Then the user is not updated and a ConstraintException is thrown")
    void givenValidUserDataThatViolationConstraints_whenApplyCalled_thenUserNotUpdatedAndThrowConstraintException(){
        // Mocks Setting
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );
        when( this.userEmailConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));
        when( this.userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));
        when( this.userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));
        when( this.userImageConstraint.check( any( User.class ))).thenReturn( Optional.of( violation ));

        // Given Valid User
        final User user = createNewUser();

        // Check that UpdatedAt is empty
        assertTrue( user.getUpdatedAt().isEmpty(), "UpdatedAt should be empty" );

        // When update data
        final UserEditor editor = user.update()
                .email( new Email("new@mail.cc" ))
                .username( new UserUsername( "new" ))
                .image( MediaId.random() )
                .birthdate( LocalDate.of( 2000, 1, 5 ));

        // When editor apply changes
        final ConstraintException exception =  assertThrows( ConstraintException.class,
                () -> editor.apply( this.userEmailConstraint,
                                    this.userUsernameConstraint,
                                    this.userBirthdateConstraint,
                                    this.userImageConstraint ));

        // Then check that constraints were checked
        verify( this.userEmailConstraint ).check( any( User.class ) );
        verify( this.userUsernameConstraint ).check( any( User.class ) );
        verify( this.userBirthdateConstraint ).check( any( User.class ) );
        verify( this.userBirthdateConstraint ).check( any( User.class ) );

        // Then assert thrown expected exception and no changes were made
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 4, violations.size() );
        assertTrue( user.getUpdatedAt().isEmpty(), "UpdatedAt should still be empty" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link UserEditor} actualiza un {@link User} sin nuevos datos, el editor devuelve
     * {@code false}, no actualiza ningún campo de la instacia {@link User} y no lanza la excepción {@link ConstraintException}.
     */
    @Test
    @DisplayName( "Given no changes, When apply is called, Then it returns false, the user is not updated, and no constraints are triggered")
    void givenNoChanges_whenApplyCalled_thenReturnFalseAndUserNotUpdatedAndConstraintsNotTriggered(){
        // Mocks Setting
        when( this.userEmailConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userUsernameConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userBirthdateConstraint.check( any( User.class ))).thenReturn( Optional.empty() );
        when( this.userImageConstraint.check( any( User.class ))).thenReturn( Optional.empty() );

        // Given Valid User
        final User user = createNewUser();

        // Check that UpdatedAt is empty
        assertTrue( user.getUpdatedAt().isEmpty(), "UpdatedAt should be empty" );

        // When update without changes and apply
        final boolean changed = assertDoesNotThrow( () -> user.update().apply( this.userEmailConstraint,
                                                                               this.userUsernameConstraint,
                                                                               this.userBirthdateConstraint,
                                                                               this.userImageConstraint ));

        // Then check that constraints were checked
        verify( this.userEmailConstraint, never() ).check( any( User.class ) );
        verify( this.userUsernameConstraint, never() ).check( any( User.class ) );
        verify( this.userBirthdateConstraint, never() ).check( any( User.class ) );
        verify( this.userBirthdateConstraint, never() ).check( any( User.class ) );

        // Then assert that return false and no changes were made
        assertFalse( changed, "Should not have changed" );
        assertTrue( user.getUpdatedAt().isEmpty(), "UpdatedAt should still be empty" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comrpueba que la clase {@link UserEditor} devuelve {@code false} si no se han actualizado los campos del
     * {@link User} con un nuevo valor.
     */
    @Test
    @DisplayName( "Given no changes, When hasChanges is called, Then it returns false" )
    void givenNoChanges_whenHasChangesCalled_thenReturnFalse(){
        // Given Valid User
        final User user = createNewUser();

        // When Update and hasChanges called Then return false
        final String message = "Should not have changes";
        assertFalse( user.update().email( user.getEmail() ).hasChanges(), message );
        assertFalse( user.update().username( user.getUsername() ).hasChanges(), message );
        assertFalse( user.update().role( user.getRole() ).hasChanges(), message );
        assertFalse( user.update().image( user.getImage().orElse( null )).hasChanges(), message );
        assertFalse( user.update().name( user.getName() ).hasChanges(), message );
        assertFalse( user.update().surname( user.getSurname().orElse( null )).hasChanges(), message );
        assertFalse( user.update().birthdate( user.getBirthdate() ).hasChanges(), message );
        assertFalse( user.update().password( user.getPassword() ).hasChanges(), message );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comrpueba que la clase {@link UserEditor} devuelve {@code true} si se han actualizado alguno de los
     * campos del {@link User}.
     */
    @Test
    @DisplayName( "Given valid user data, When hasChanges is called, Then it returns true" )
    void givenValidUserData_whenHasChangesCalled_thenReturnTrue(){
        // Given Valid User
        final User user = createNewUser();

        // When Update and hasChanges called Then return true
        final String message = "Should have changes";
        assertTrue( user.update().email( new Email("new@mail.cc" )).hasChanges(), message );
        assertTrue( user.update().username( new UserUsername( "new" )).hasChanges(), message );
        assertTrue( user.update().role( UserRole.ADMIN ).hasChanges(), message );
        assertTrue( user.update().image( MediaId.random() ).hasChanges(), message );
        assertTrue( user.update().name( new UserName( "Name New" )).hasChanges(), message );
        assertTrue( user.update().surname( new UserSurname( "Surname New" )).hasChanges(), message );
        assertTrue( user.update().birthdate( LocalDate.of( 2000, 1, 5 )).hasChanges(), message );
        assertTrue( user.update().password( new EncodedPassword( "new-encoded-password" )).hasChanges(), message );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
