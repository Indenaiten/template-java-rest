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

//--------------------------------------------------------------------------------------------------------------------\\
//---| HELPER METHODS |-----------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    private User createNewUser(){
        final UserId id = UserId.random();
        final Email email = new Email( "test@mail.cc" );
        final UserUsername username = new UserUsername( "test" );
        final UserRole role = UserRole.USER;
        final MediaId image = MediaId.random();
        final UserName name = new UserName( "Test" );
        final UserSurname surname = new UserSurname( "User" );
        final LocalDate birthdate = LocalDate.of( 1993, 2, 10 );
        final EncodedPassword password = new EncodedPassword( "encoded-password" );
        final Timestamp createdAt = Timestamp.now();

        return new User( id, email, username, role, image, name, surname, birthdate, password, createdAt, null );
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
    @DisplayName( "UserEditor succeeds without changes and does not trigger constraints" )
    void givenNoChanges_whenApplyingEditor_thenUserIsUpdatedSuccessfullyAndReturnFalse() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // When apply update without changes
        final boolean changed = user.update().apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertFalse( changed, "Should not have changed" );
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isEmpty(), "UpdatedAt should not have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor fails with ConstraintException when constraint violations exist")
    void givenInvalidUserDataAndConstraints_whenApplyingEditor_thenNotApplyChangesAndThrowConstraintException() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked ConstraintViolation that constraints will be return
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );

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

        // When apply update without changes
        final UserEditor editor = user.update().email( new Email( "new@mail.cc" ))
                                               .username( new UserUsername( "new_username" ))
                                               .birthdate( LocalDate.of( 2000, 2, 10 ))
                                               .image( MediaId.random() );
        final ConstraintException exception =  assertThrows( ConstraintException.class, () ->
                editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint ));

        // Then check that constraints were checked
        verify( userEmailConstraint ).check( any( User.class ));
        verify( userUsernameConstraint ).check( any( User.class ));
        verify( userBirthdateConstraint ).check( any( User.class ));
        verify( userImageConstraint ).check( any( User.class ));

        // Then assert thrown exception
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 4, violations.size() );
        assertTrue( user.getUpdatedAt().isEmpty(), "UpdatedAt should not have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only email and triggers only UserEmailConstraint" )
    void givenEmailChanged_whenApplyingEditor_thenUpdatesEmailAndChecksTriggerUserEmailConstraint() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Email
        final Email newEmail = new Email( "new@mail.cc" );

        // Set new Email in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).email( newEmail );

        // Check of Email not changed in User
        assertNotEquals( newEmail, user.getEmail() );

        // When apply update with only email change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newEmail, user.getEmail() );
        verify( userEmailConstraint ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userImageConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only username and triggers only UserUsernameConstraint" )
    void givenUsernameChanged_whenApplyingEditor_thenUpdatesUsernameAndChecksTriggerUserUsernameConstraint() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Username
        final UserUsername newUsername = new UserUsername( "new_username" );

        // Set new Username in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).username( newUsername );

        // Check of Username not changed in User
        assertNotEquals( newUsername, user.getUsername() );

        // When apply update with only username change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newUsername, user.getUsername() );
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userImageConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only birthdate and triggers only UserBirthdateConstraint" )
    void givenBirthdateChanged_whenApplyingEditor_thenUpdatesBirthdateAndChecksTriggerUserBirthdateConstraint() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Birthdate
        final LocalDate newBirthdate = LocalDate.of( 2000, 2, 10 );

        // Set new Birthdate in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).birthdate( newBirthdate );

        // Check of Birthdate not changed in User
        assertNotEquals( newBirthdate, user.getBirthdate() );

        // When apply update with only birthdate change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newBirthdate, user.getBirthdate() );
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint ).check( any( User.class ));
        verify( userImageConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only image and triggers only UserImageConstraint" )
    void givenImageChanged_whenApplyingEditor_thenUpdatesImageAndChecksTriggerUserImageConstraint() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Image
        final MediaId newImage = MediaId.random();

        // Set new Image in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).image( newImage );

        // Check of Image not changed in User
        assertNotEquals( newImage, user.getImage().orElse( null ));

        // When apply update with only image change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newImage, user.getImage().orElse( null ));
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userImageConstraint ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only role and does not trigger constraints" )
    void givenRoleChanged_whenApplyingEditor_thenUpdatesRoleAndConstraintsNotTriggered() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Role
        final UserRole newRole = UserRole.ADMIN;

        // Set new Role in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).role( newRole );

        // Check of Role not changed in User
        assertNotEquals( newRole, user.getRole() );

        // When apply update with only role change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newRole, user.getRole() );
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userImageConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only name and does not trigger constraints" )
    void givenNameChanged_whenApplyingEditor_thenUpdatesNameAndConstraintsNotTriggered() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Name
        final UserName newName = new UserName( "New Name" );

        // Set new Name in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).name( newName );

        // Check of Name not changed in User
        assertNotEquals( newName, user.getName() );

        // When apply update with only name change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newName, user.getName() );
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userImageConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only surname and does not trigger constraints" )
    void givenSurnameChanged_whenApplyingEditor_thenUpdatesSurnameAndConstraintsNotTriggered() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Surname
        final UserSurname newSurname = new UserSurname( "New Surname" );

        // Set new Surname in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).surname( newSurname );

        // Check of Surname not changed in User
        assertNotEquals( newSurname, user.getSurname().orElse( null ));

        // When apply update with only surname change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newSurname, user.getSurname().orElse( null ));
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userImageConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "UserEditor updates only password and does not trigger constraints" )
    void givenPasswordChanged_whenApplyingEditor_thenUpdatesPasswordAndConstraintsNotTriggered() {
        // Given Valid User
        final User user = this.createNewUser();

        // Given mocked Constraints
        final UserEmailConstraint userEmailConstraint = this.mockUserEmailConstraintOk();
        final UserUsernameConstraint userUsernameConstraint = this.mockUserUsernameConstraintOK();
        final UserBirthdateConstraint userBirthdateConstraint = this.mockUserBirthdateConstraintOK();
        final UserImageConstraint userImageConstraint = this.mockUserImageConstraintOK();

        // Given new Password
        final EncodedPassword newPassword = new EncodedPassword( "new-password" );

        // Set new Password in Editor but not apply changes
        final UserEditor editor = new UserEditor( user ).password( newPassword );

        // Check of Password not changed in User
        assertNotEquals( newPassword, user.getPassword() );

        // When apply update with only password change
        final boolean changed = editor.apply( userEmailConstraint, userUsernameConstraint, userBirthdateConstraint, userImageConstraint );

        // Then
        assertTrue( changed, "Should have changes" );
        assertEquals( newPassword, user.getPassword() );
        verify( userEmailConstraint, never() ).check( any( User.class ));
        verify( userUsernameConstraint, never() ).check( any( User.class ));
        verify( userBirthdateConstraint, never() ).check( any( User.class ));
        verify( userImageConstraint, never() ).check( any( User.class ));
        assertTrue( user.getUpdatedAt().isPresent(), "UpdatedAt should have changed" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
